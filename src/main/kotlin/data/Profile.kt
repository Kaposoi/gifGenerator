package data

import data.content.GifGeneratorConfig
import utils.GsonUtils
import java.io.File

object Profile {
    var profile: GifGeneratorConfig

    init {
        val configDir = "${System.getProperty("user.dir")}\\config"
        val fileConfigDir = File(configDir)

        if (!fileConfigDir.exists()) {
            fileConfigDir.mkdirs()
        }

        val configPath = "${configDir}\\config.json"
        val fileConfigPath = File(configPath)

        if (!fileConfigDir.exists()) {
            fileConfigDir.createNewFile()
            fileConfigPath.writeText("{\n" +
                    "  \"repeat\": 0,\n" +
                    "  \"delay\": 35,\n" +
                    "  \"maxSize\": [350, 200]\n" +
                    "}")
        }

        profile = GsonUtils.gson.fromJson(fileConfigPath.readText(), GifGeneratorConfig().javaClass)
    }
}
