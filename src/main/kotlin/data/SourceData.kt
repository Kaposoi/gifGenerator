package data

data class SourceData(
    val width: Int,
    val height: Int,
    val type: String,
    val avatar: List<Avatar>,
    val text: List<String>,
    val imageNum: Int
)