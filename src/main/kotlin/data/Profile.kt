package data

object Profile {
    // gif重复播放多少次, 0意味着Infinite, 默认是0
    var repeat: Int = 0

    // 相邻帧的间隔(毫秒), 默认是35
    var delay: Int = 35

    // 生成的gif的最大大小， 默认是350 * 200, 如果gif原图没有这个尺寸， 会对gif进行按原比例缩放， 缩放后的最长边不会超过对应的尺寸
    var maxSize: Pair<Int, Int> = Pair(350, 200)

    // 单例类, 相当于static init
    init {

    }

    fun reSetConfig(repeat: Int, delay:Int, maxSize: Pair<Int, Int>) {
        this.repeat = repeat
        this.delay = delay
        this.maxSize = maxSize
    }
}
