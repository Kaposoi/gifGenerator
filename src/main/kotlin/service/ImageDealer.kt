package service

import com.madgag.gif.fmsware.AnimatedGifEncoder
import data.Profile
import data.SourceData
import data.content.GifGeneratorConfig
import utils.GsonUtils
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File

/**
 * 处理图像的主体
 */
class ImageDealer(
    private val sourceName: String,
    private val outputPath: String = "${System.getProperty("user.dir")}\\cache\\output.gif",
    private val pasteImagePath: String = "${System.getProperty("user.dir")}\\cache\\in.jpg"
) {
    private val userDir = System.getProperty("user.dir")
    private val dataSourcePath = "${userDir}\\source\\${sourceName}"

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

    /**
     * 不保持原比例的缩放
     */
    private fun resizeImage(image: BufferedImage, toWidth: Int, toHeight: Int): Image {
        return image.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH)
    }

    /**
     * 有限制的等比缩放, 采取先进行图片合成， 再对合成后的图片进行缩放的策略
     * @see GifGeneratorConfig.maxSize
     */
    private fun resizeImageWithDistrict(image: BufferedImage, toWidth: Int, toHeight: Int): Image {
        val width = image.width
        val height = image.height

        return if (width > height) {
            image.getScaledInstance(toWidth, (1.0 * toHeight * (1.0 * toWidth / width)).toInt(), Image.SCALE_SMOOTH)
        } else {
            image.getScaledInstance((1.0 * toWidth * (1.0 * toHeight / height)).toInt(), toHeight, Image.SCALE_SMOOTH)
        }
    }

    /**
     * 生成一帧图片
     */
    private fun generatorFrame(width: Int, height:Int, pos: List<Int>, index: Int): BufferedImage {
        val newImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = newImage.createGraphics()

        graphics.color = Color.WHITE

        val sourceImage = SourceParser.getBufferedImage("${dataSourcePath}\\${index}.png")
        val pasteImage = SourceParser.getBufferedImage(pasteImagePath)

        // 为0意味着不需要paste
        if (pos[2] != 0) {
            graphics.drawImage(resizeImage(pasteImage, pos[2], pos[3]), pos[0], pos[1], pos[2], pos[3], null)
        }

        graphics.drawImage(sourceImage, 0, 0, sourceImage.width, sourceImage.height, null)

        return newImage
    }

    fun generatorFrames() {
        val sourceData = GsonUtils.gson.fromJson(File("${dataSourcePath}\\data.json").readText(), SourceData::class.java)

        when (sourceData.imageNum) {
            1 -> {
                for ((index, e) in sourceData.avatar[0].pos.withIndex()) {
                    encoder.setDelay(Profile.profile.delay)
                    encoder.addFrame(generatorFrame(sourceData.width, sourceData.height, e, index))
                }
            }
            else -> {}
        }

        encoder.finish()
    }
}