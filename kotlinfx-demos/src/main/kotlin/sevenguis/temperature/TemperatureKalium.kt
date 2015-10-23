package demos.sevenguis.temperaturekalium

import javafx.application.Application
import javafx.stage.Stage
import kotlinfx.builders.*
import kotlinfx.kalium.text

fun main(args: Array<String>) {
    Application.launch(TemperatureConverterKalium::class.java)
}

class TemperatureConverterKalium : Application() {
    override fun start(stage: Stage?) {
        val celsius = TextField()
        val fahrenheit = TextField()

        celsius.text { if (isNumeric(fahrenheit.text())) fToC(fahrenheit.text()) else celsius.text }
        fahrenheit.text { if (isNumeric(celsius.text())) cToF(celsius.text()) else fahrenheit.text }

        Stage(stage, title = "Temperature Converter") {
            scene = Scene {
                root = HBox(spacing = 10.0, padding = Insets(10.0)) {
                    +celsius + Label("Celsius =") + fahrenheit + Label("Fahrenheit")
                }
            }
        }.show()
    }
}

fun cToF(c: Double) = (9 / 5.0 * c) + 32
fun fToC(f: Double) = 5 / 9.0 * (f - 32)
fun cToF(c: String) = Math.round(cToF(c.toDouble())).toString()
fun fToC(f: String) = Math.round(fToC(f.toDouble())).toString()
fun isNumeric(s: String): Boolean {
    try {
        s.toDouble()
    } catch (e: Exception) {
        return false
    }
    return true
}
