import org.apache.commons.io.FileUtils
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

template = FileUtils.readFileToString(new File(CONTEXT.templateDir + "BASEMAPPER.vm"))

// 渲染模板
StringWriter writer = new StringWriter()
Velocity.evaluate(new VelocityContext(CONTEXT), writer, "", template.replaceAll("[ ]*" +
        "(#if|#else|#elseif|#end|#set|#foreach)", "\$1"))

// 创建目录
def dir = new File(CONTEXT.dir.toString() + File.separator + "dataobject" + File.separator + "base")
dir.mkdirs()

// 生成文件
new File(dir, CONTEXT.BASEMAPPER.fileName)
        .withPrintWriter { out -> out.println(writer.toString()) }
