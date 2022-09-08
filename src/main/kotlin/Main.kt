import data.DealerConfig
import service.ImageDealer

fun main() {
    val dir = System.getProperty("user.dir")

    ImageDealer(
        DealerConfig("wallpaper",
        "${dir}\\cache",
        listOf("${dir}\\cache\\in.png"))
    ).generator()
}