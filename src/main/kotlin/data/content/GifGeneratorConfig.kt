package data.content

class GifGeneratorConfig {
    /**
     * gif重复播放多少次, 0意味着Infinite, 默认是0
     */
    var repeat: Int = 0

    /**
     * 生成的gif的最大大小， 默认是350 * 200, 如果gif原图没有这个尺寸， 会对gif进行按原比例缩放， 缩放后的最长边不会超过对应的尺寸
      */
    var maxSize: List<Int> = listOf(350, 200)

    fun resetConfig(repeat: Int = 0, maxSize: List<Int> = listOf(350, 200)) {
        this.repeat = repeat
        this.maxSize = maxSize
    }
}