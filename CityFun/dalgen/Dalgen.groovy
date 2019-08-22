import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil
import com.intellij.database.model.DasTable
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.time.FastDateFormat
import groovy.lang.Binding
import groovy.json.JsonSlurper

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */
// 对应模板的位置
dalgenDir = PROJECT.basePath + "/dalgen/"

/**
 * 选择对应的目标文件夹
 */
FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    this.dir = dir

    SELECTION.filter { it instanceof DasTable }
            .each {
                generate(it, dir)
            }
}

/**
 * 生成上下文和文件
 * @param table 表
 * @param dir 目标目录
 */
def generate(table, dir) {
    context = buildCommonContext(table, dir)

    context.put("POJO", POJO(table, context))
    context.put("DAO", DAO(table, context))
    context.put("BASEDAO", BASEDAO(table, context))
    context.put("BASEMAPPER", BASEMAPPER(table, context))
    context.put("MAPPER", MAPPER(table, context))

    Binding binding = new Binding()
    binding.setVariable("LOG", LOG)
    binding.setVariable("CONTEXT", context)

    String[] arr1 = [dalgenDir+'groovy/']
    GroovyScriptEngine gse = new GroovyScriptEngine(arr1)
    gse.run("POJO.groovy", binding)
    gse.run("DAO.groovy", binding)
    gse.run("BASEDAO.groovy", binding)
    gse.run("BASEMAPPER.groovy", binding)
    gse.run("MAPPER.groovy", binding)

}

// ------------- 生成方法
/**
 * 生成POJO
 */
def POJO(table, commonContext) {
    def simpleClassName = javaName(table.name, true) + 'DO'
    def pojoPackage = commonContext.rootPackage + ".dataobject"
    // 返回信息
    map = [
            "package"        : pojoPackage,
            "simpleClassName": simpleClassName,
            "fullClassName"  : pojoPackage + '.' + simpleClassName,
            "fileName"       : simpleClassName + ".java"
    ]
    return map
}

/**
 * 生成DAO
 */
def DAO(table, commonContext) {
    def simpleClassName = javaName(table.name, true) + 'DAO'
    def daoPackage = commonContext.rootPackage + ".dao"
    // 返回信息
    map = [
            "simpleClassName": simpleClassName,
            "package"        : daoPackage,
            "fullClassName"  : daoPackage + '.' + simpleClassName,
            "fileName"       : simpleClassName + '.java'
    ]
    return map
}

/**
 * 生成BASEDAO
 */
def BASEDAO(table, commonContext) {
    def simpleClassName = 'Base' + javaName(table.name, true) + 'DAO'
    def daoPackage = commonContext.rootPackage + ".dao.base"
    // 返回信息
    map = [
            "simpleClassName": simpleClassName,
            "package"        : daoPackage,
            "fullClassName"  : daoPackage + '.' + simpleClassName,
            "fileName"       : simpleClassName + '.java'
    ]
    return map
}

/**
 * 生成mapper.xml
 */
def BASEMAPPER(table, commonContext) {
    localPackage = "dataobject.base"
    templateName = "BASEMAPPER.vm"

    map = [
            "fileName": 'Base' + javaName(table.name, true) + 'Mapper.xml'
    ]
    return map
}

def MAPPER(table, commonContext) {
    localPackage = "dataobject"
    templateName = "MAPPER.vm"

    map = [
            "fileName": javaName(table.name, true) + 'DO.xml'
    ]
    return map
}


// ------------- 工具方法
/**
 * 构造公共context
 * @param table
 * @param dir
 * @return
 */
def buildCommonContext(table, dir) {
    map = [
            "author"     : "dalgen",
            "templateDir": dalgenDir + 'templates/',
            "dir"        : dir,
            "now"        : FastDateFormat.getInstance("yyyy-MM-dd").format(new Date()),
            "rootPackage": mappingDirToPackageRoot(dir),
            "table"      : [
                    "name"   : table.name,
                    "comment": table.comment,
                    "columns": calcFields(table)
            ]
    ]

    // 补充自定义文件
    def configFile = new File(dalgenDir + "config.json")
    if (configFile.exists()) {
        def config = new JsonSlurper().parseText(FileUtils.readFileToString(configFile))
        map.putAll(config)
    }
    return map
}

/**
 * 获取包名称
 * @param dir 实体类所在目录
 * @return
 */
static def mappingDirToPackageRoot(dir) {
    def target = dir.toString().replaceAll("/", ".")
            .replaceAll("^.*src(\\.main\\.java\\.)?", "")
    return target.charAt(0) != (char) '.' ? target : target.substring(1)
}

/**
 * 拿到一张表对应的所有的字段,并且扩充模型
 * @param table 数据库表
 * @return 字段Object
 */
static def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        fields += [
                jdbcName              : col.getName(),
                jdbcType              : Case.LOWER.apply(col.getDataType().getSpecification()),
                variableName          : javaName(col.getName(), false),
                capitalizeVariableName: javaName(col.getName(), true),
                javaType              : javaType(col.getDataType()),
                mybatisType           : mybatisType(col.getDataType()),
                comment               : col.getComment()
        ]
    }
}

/**
 * 获取字段对应Java变量名称
 */
static def javaName(str, capitalize) {
    def s = str.split(/(?<=[^\p{IsLetter}])/).collect { Case.LOWER.apply(it).capitalize() }
            .join("").replaceAll("_", "")
    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}


static def mybatisType(dataType) {
    switch (dataType.typeName) {
        case "int":
            return "INTEGER"
        case "text":
            return "LONGVARBINARY"
        case "datetime":
            return "TIMESTAMP"
        default:
            return Case.UPPER.apply(dataType.typeName)

    }
}

static def javaType(dataType) {
    switch (dataType.typeName) {
        case "tinyint":
        case "smallint":
            return "Integer"

        case "bigint":
            return "Long"

        case "int":
            return dataType.getLength() > 10 ? "Long" : "Integer"

        case "float":
        case "double":
        case "decimal":
        case "real":
            return "Double"

        case "date":
        case "datetime":
        case "timestamp":
            return "java.util.Date"

        case "time":
            return "java.sql.Time"

        default:
            return "String"

    }

}
