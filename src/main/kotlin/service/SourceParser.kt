package service

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * 单例， 做一些资源的处理工作
 */
object SourceParser {
    /**
     * 根据图像路径返回得到的图像对象
     */
    fun getBufferedImage(path: String): BufferedImage? {
        return ImageIO.read(File(path))
    }
}