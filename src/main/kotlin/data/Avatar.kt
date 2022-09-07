package data

data class Avatar(
    // 用于给有多张图片的情况标记位置
    val type: String,

    val pos: List<List<Int>>,

    // 默认情况是paste图在下， 而原图在上， 这个开关将paste图覆盖在原图之上
    val avatarOnTop: Boolean = false,

    // 用于指示是否使用圆形图像
    val circle: Boolean = false
)
