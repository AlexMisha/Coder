import Decoder.getBytes
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.StandardOpenOption

fun main(args: Array<String>) {
    Coder().main()
}

class Coder {
    private lateinit var decoder: IDecoder
    private lateinit var inputFiles: Set<CharsetFile>
    private lateinit var outputFiles: Set<CharsetFile>

    fun main() {
        init()
        inputFiles.forEach {
            val decodedBytes = decoder.decode(read(it.path), it.charset)
            write(outputFiles.elementAt(0).path, decodedBytes)
        }

        val decoded = decoder.decode(read(outputFiles.elementAt(0).path), outputFiles.elementAt(0).charset)
        write(outputFiles.elementAt(1).path, decoded)
    }

    private fun init() {
        decoder = Decoder()

        val prefix = "src/main/resources"
        inputFiles = setOf(
                CharsetFile("$prefix/sample1.txt", Charset.forName("CP866")),
                CharsetFile("$prefix/sample2.txt", Charset.forName("ISO-8859-5")),
                CharsetFile("$prefix/sample3.txt", Charset.forName("ISO-8859-5")))
        outputFiles = setOf(
                CharsetFile("$prefix/output1.txt", Charset.forName("windows-1251")),
                CharsetFile("$prefix/output2.txt", Charsets.UTF_8))
    }
}

data class CharsetFile(val path: String, val charset: Charset)

interface IDecoder {
    fun decode(src: ByteArray, srcCharset: Charset): ByteArray
}

fun read(path: String): ByteArray = Files.readAllBytes(File(path).toPath())

fun write(path: String, text: ByteArray) {
    val file = File(path)
    when {
        file.exists() -> Files.write(file.toPath(), text, StandardOpenOption.APPEND)
        else -> Files.write(file.toPath(), text)
    }
    Files.write(file.toPath(), listOf("\n"), StandardOpenOption.APPEND)
}

fun ByteArray.toUTF8(): ByteArray {
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

    val result = mutableListOf<Byte>()
    forEach {
        when {
            unicode.containsKey(it) -> getBytes(unicode[it]).forEach { result.add(it) }
            else -> result.add(it)
        }
    }

    return result.toByteArray()
}

fun fromCP1251ToUTF8(src: ByteArray) = src.toUTF8()