import com.madgag.gif.fmsware.AnimatedGifEncoder
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

//fun main(args: Array<String>) {
//    val dir = System.getProperty("user.dir")
//    val imageDir = "${dir}\\image"
//
//    val sourcePath = "${imageDir}\\0.png"
//    val toPath = "${imageDir}\\testin.jpg"
//
//    val sourceBufImage = ImageIO.read(File(sourcePath))
//    val toBufImage = ImageIO.read(File(toPath))
//
//    val sourceData = Pair(sourceBufImage.width, sourceBufImage.height)
//
//    val pos = mutableListOf(Pair(180, 60), Pair(184, 75), Pair(183, 98), Pair(179, 118),
//        Pair(156, 194), Pair(178, 136), Pair(175, 66), Pair(170, 42),
//        Pair(175, 34), Pair(179, 35), Pair(180, 54), Pair(183, 58),
//        Pair(174, 35), Pair(179, 35), Pair(181, 54), Pair(182, 59),
//        Pair(183, 71), Pair(180, 131))
//
//    val resizeParam = mutableListOf(Pair(100, 100), Pair(100, 100), Pair(100, 100), Pair(110, 100),
//        Pair(150, 48), Pair(122, 69), Pair(122, 85), Pair(130, 96),
//        Pair(118, 95), Pair(110, 93), Pair(102, 93), Pair(97, 92),
//        Pair(120, 94), Pair(109, 93), Pair(101, 92), Pair(98, 92),
//        Pair(90, 96), Pair(92, 101))
//
//    val encoder = AnimatedGifEncoder()
//    encoder.start("${imageDir}\\output.gif")
//    encoder.setRepeat(0)
//
//    for ((index, e) in pos.withIndex()) {
//        val sourceImagePath = "${imageDir}\\${index}.png"
//        val s = ImageIO.read(File(sourceImagePath))
//
//        encoder.setDelay(35)
//        val newImage = BufferedImage(sourceData.first, sourceData.second, BufferedImage.TYPE_INT_RGB)
//        val graphics = newImage.createGraphics()
//
//        graphics.color = Color.WHITE
//
//        val resize = resizeParam[index]
//
//        val imgResize = toBufImage.getScaledInstance(resize.first, resize.second, Image.SCALE_SMOOTH)
//
//        graphics.drawImage(imgResize, e.first, e.second, resize.first, resize.second, null)
//        graphics.drawImage(s, 0, 0, s.width, s.height, null)
//
//        encoder.addFrame(newImage)
//    }
//
//    for (i in 19..22) {
//        val sourceImagePath = "${imageDir}\\${i}.png"
//        val s = ImageIO.read(File(sourceImagePath))
//
//        encoder.setDelay(35)
//        encoder.addFrame(s)
//    }
//
//    encoder.finish()
//}

fun main() {
    val outputPath: String = "${System.getProperty("user.dir")}\\cache\\output.gif"
    val t = outputPath.split("\\")
    val len = t.size

    val outputDir = t.filterIndexed { index, _ -> index != len - 1  }.joinToString("\\")

    println(outputDir)
}