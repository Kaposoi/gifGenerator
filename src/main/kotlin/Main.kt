import data.DealerConfig
import service.ImageDealer

fun main() {
    val dir = System.getProperty("user.dir")

    ImageDealer(
        DealerConfig("anyasuki",
        "${dir}\\cache",
        listOf("${dir}\\cache\\in.jpg"))
    ).generator()
}