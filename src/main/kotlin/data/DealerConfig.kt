package data

import service.ImageDealer

/**
 * ImageDealer的配置
 * @see ImageDealer
 */
data class DealerConfig(
    val sourceName: String,
    val outputPathDir: String = "${System.getProperty("user.dir")}\\cache",
    val pasteImagePath: List<String> = listOf("${System.getProperty("user.dir")}\\cache\\in.jpg")
)
