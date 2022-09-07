package data

data class SourceData(
    val width: Int,
    val height: Int,
    val type: String = "gif",
    val avatar: List<Avatar>,
    val text: List<TextContent>,

    // 有几张图片需要覆盖， 其实这个就是avatar.size
    val imageNum: Int = 1,
    val delay: Int = 100
)