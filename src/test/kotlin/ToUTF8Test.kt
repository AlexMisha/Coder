import Decoder.getBytes
import org.junit.Test
import java.io.File
import java.nio.file.Files

class ToUTF8Test {
    @Test
    fun toUTF8Test() {
        val unicodeList = listOf("\u0410", "\u0411", "\u0412", "\u0413", "\u0414", "\u0415", "\u0416", "\u0417",
                "\u0418", "\u0419", "\u041A", "\u041B", "\u041C", "\u041D", "\u041E", "\u041F", "\u0420", "\u0421",
                "\u0422", "\u0423", "\u0424", "\u0425", "\u0426", "\u0427", "\u0428", "\u0429", "\u042A", "\u042B",
                "\u042C", "\u042D", "\u042E", "\u042F", "\u0430", "\u0431", "\u0432", "\u0433", "\u0434", "\u0435",
                "\u0436", "\u0437", "\u0438", "\u0439", "\u043A", "\u043B", "\u043C", "\u043D", "\u043E", "\u043F",
                "\u0440", "\u0441", "\u0442", "\u0443", "\u0444", "\u0445", "\u0446", "\u0447", "\u0448", "\u0449",
                "\u044A", "\u044B", "\u044C", "\u044D", "\u044E", "\u044F")


        val unicode = mutableMapOf<Byte, String>()

        for ((k, i) in (-79 + 0xF..-16 + 0xF).withIndex()) {
            unicode[i.toByte()] = unicodeList[k]
        }

        println(unicode)

        val bytes = Files.readAllBytes(File("src/main/resources/output1.txt").toPath())
        val result = mutableListOf<Byte>()
        bytes.forEach {
            print("$it ")
            if (unicode.containsKey(it)) {
                val uStringBytes = getBytes(unicode[it])
                uStringBytes.forEach { result.add(it) }
            } else {
                result.add(it)
            }
        }

        Files.write(File("src/main/resources/output2.txt").toPath(), result.toByteArray())
    }
}