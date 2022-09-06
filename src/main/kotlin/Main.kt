import com.madgag.gif.fmsware.AnimatedGifEncoder
import data.Profile
import service.ImageDealer
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun main() {
    val dir = System.getProperty("user.dir")

    ImageDealer("kurumi",
        "${dir}\\cache\\output.gif",
        "${dir}\\cache\\in.jpg").generatorFrames()
}