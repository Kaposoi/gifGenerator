package service

import com.madgag.gif.fmsware.AnimatedGifEncoder
import data.OverlayData
import data.Profile
import java.awt.image.BufferedImage
import java.io.File

/**
 * 处理图像的主体
 */
class ImageDealer(
    dataSourcePath: List<String> = listOf(),
    outputPath: String = "${System.getProperty("user.dir")}\\cache\\output.gif",
    pasteImage: BufferedImage
) {
    private val userDir = System.getProperty("user.dir")

    private lateinit var posList: List<OverlayData>

    // 初始化一个编码器
    private var encoder = AnimatedGifEncoder()

    init {
        val t = outputPath.split("\\")
        val len = t.size

        val outputDir = t.filterIndexed { index, _ -> index != len - 1  }.joinToString("\\")
        val dir = File(outputDir)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        encoder.start(outputPath)
        encoder.setRepeat(Profile.profile.repeat)
    }

    private fun generatorFrame(data: OverlayData) {

    }
}