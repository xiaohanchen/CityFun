import org.apache.commons.io.FileUtils
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

template = FileUtils.readFileToString(new File(CONTEXT.templateDir + "DAO.vm"))

// 渲染模板
StringWriter writer = new StringWriter()
Velocity.evaluate(new VelocityContext(CONTEXT), writer, "", template.replaceAll("[ ]*" +
        "(#if|#else|#elseif|#end|#set|#foreach)", "\$1"))

// 创建目录
def pojoDir = new File(CONTEXT.dir.toString() + File.separator + "dao")
pojoDir.mkdirs()

// 生成文件
def olfFile = new File(pojoDir, CONTEXT.DAO.fileName)

// 不存在才生成
if (!olfFile.exists()) {
    olfFile.withPrintWriter { out -> out.println(writer.toString()) }
}
