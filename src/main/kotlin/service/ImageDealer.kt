package service

import com.madgag.gif.fmsware.AnimatedGifEncoder
import data.*
import utils.GsonUtils
import java.awt.Color
import java.awt.Font
import java.awt.Image
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * 处理图像的主体
 */
class ImageDealer(config: DealerConfig) {
    private val sourceName = config.sourceName
    private val outputPathDir = config.outputPathDir
    private val pasteImagePath = config.pasteImagePath

    private val userDir = System.getProperty("user.dir")
    private val dataSourcePath = "${userDir}\\source\\${sourceName}"

    private val sourceData = GsonUtils.gson.fromJson(File("${dataSourcePath}\\data.json").readText(), SourceData::class.java)

    // 指定输出文件的路径， 生成的文件格式由data.json配置
    private val outputPath = "${outputPathDir}\\output.${sourceData.type.lowercase()}"

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
     * 将图片裁剪成圆形
     */
    private fun toCircle(image: BufferedImage): BufferedImage {
        // 创建一个透明的BI
        val newImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_4BYTE_ABGR)

        // 创建一个椭圆形状
        val shape = Ellipse2D.Double(0.0, 0.0, image.width.toDouble(), image.height.toDouble())

        val graphics = newImage.createGraphics()

        graphics.clip = shape
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.drawImage(image, 0, 0, null)

        return newImage
    }

    /**
     * 在图像上绘制文字, 引用传参
     */
    private fun drawText(image: BufferedImage, text: TextContent) {
        val fontPath = "${userDir}\\source\\font"

        val graphics = image.createGraphics()

        val font = "MiSans Bold"

        graphics.color = Color.getColor(text.color)
        graphics.font = Font(font, Font.CENTER_BASELINE, text.size)
        graphics.drawString(text.text, text.pos[0], text.pos[1])
    }

    /**
     * 生成一帧图片, 进行paste图片和添加文字, 返回处理后的图像
     * 只要有index便可确定图片的覆盖位置
     * 在覆盖的时候， 要覆盖的图片和data.json中的avatar对应
     */
    private fun generatorFrame(width: Int, height:Int, index: Int): BufferedImage {
        val sourceImage = SourceParser.getBufferedImage("${dataSourcePath}\\${index}.png")
        val newImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = newImage.createGraphics()

        val imageNum = sourceData.avatar.size

        graphics.color = Color.WHITE

        for ((idx, e) in sourceData.avatar.withIndex()) {
            val pos = e.pos[index]
            var toImage = SourceParser.getBufferedImage(pasteImagePath[idx])

            // 启动circle时， 将图像裁剪成圆形
            if (e.circle) {
                toImage = toCircle(toImage)
            }

            // 用于旋转等操作
            val graphicsToImage = toImage.createGraphics()

            // 4号参数指定了图片旋转的角度
            if (pos.size > 4) {
                graphicsToImage.rotate(pos[4].toDouble())
            }

            // 启动avatarOnTop时， 将要覆盖的图片覆盖到原图上面, 因此画原图， 再画要覆盖的图
            if (e.avatarOnTop) {
                if (idx == 0) {
                    graphics.drawImage(sourceImage, 0, 0, sourceImage.width, sourceImage.height, null)
                }

                if (pos[2] != 0) {
                    graphics.drawImage(resizeImage(toImage, pos[2], pos[3]), pos[0], pos[1], pos[2], pos[3], null)
                }
            } else {
                if (pos[2] != 0) {
                    graphics.drawImage(resizeImage(toImage, pos[2], pos[3]), pos[0], pos[1], pos[2], pos[3], null)
                }

                if (idx == imageNum - 1) {
                    graphics.drawImage(sourceImage, 0, 0, sourceImage.width, sourceImage.height, null)
                }
            }
        }

        // 添加文字
        for (text in sourceData.text) {
            drawText(newImage, text)
        }

        return newImage
    }

    /**
     * 如果返回的图片是gif, 则调用此方法, 返回图片的类型由sourceData的type字段进行配置
     * @see SourceData.type
     */
    private fun generatorFrames() {
        val num = sourceData.avatar[0].pos.size

        for (i in 0 until num) {
            encoder.setDelay(sourceData.delay)
            encoder.addFrame(generatorFrame(sourceData.width, sourceData.height, i))
        }

        encoder.finish()
    }

    /**
     * 生成指定格式的图片, 当生成图片不是gif时调用此函数, 此时source图片只有一张
     */
    private fun generatorFormatImage(format: String) {
        val image = generatorFrame(sourceData.width, sourceData.height, 0)
        ImageIO.write(image, format, File(outputPath))
    }

    /**
     * 根据图像的返回类型生成图像
     */
    fun generator() {
        when(sourceData.type.lowercase()) {
            "gif" -> {
                generatorFrames()
            }
            "png" -> {
                generatorFormatImage(sourceData.type)
            }
        }
    }
}