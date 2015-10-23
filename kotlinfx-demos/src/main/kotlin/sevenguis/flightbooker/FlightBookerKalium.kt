package demos.sevenguis.flightbookerk

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.stage.Stage
import kotlinfx.builders.*
import kotlinfx.kalium.disable
import kotlinfx.kalium.style
import kotlinfx.kalium.text
import kotlinfx.kalium.value
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    Application.launch(FlightBookerKalium::class.java)
}

class FlightBookerKalium : Application() {
    override fun start(stage: Stage?) {
        val flightType = ComboBox(FXCollections.observableArrayList("one-way flight", "return flight"))
        flightType.value = "one-way flight"
        val startDate = TextField(LocalDate.now().asString())
        val returnDate = TextField(LocalDate.now().asString())
        val book = Button("Book")

        returnDate.disable { flightType.value() == "one-way flight" }
        startDate.style { if (startDate.text().isDate) "" else "-fx-background-color: lightcoral" }
        returnDate.style { if (returnDate.text().isDate) "" else "-fx-background-color: lightcoral" }
        book.disable {
            if (flightType.value() == "one-way flight") {
                !startDate.text().isDate
            } else {
                !startDate.text().isDate ||
                        !returnDate.text().isDate ||
                        startDate.text().asDate.compareTo(returnDate.text().asDate) > 0
            }
        }

        Stage(stage, title = "Flight Booker") {
            scene = Scene {
                root = VBox(spacing = 10.0, padding = Insets(10.0)) {
                    +flightType
                    +startDate
                    +returnDate
                    +book
                }
            }
        }.show()
    }
}

val formatter = DateTimeFormatter.ISO_LOCAL_DATE
fun LocalDate?.asString() = this!!.format(formatter)!!
val String?.asDate: LocalDate get() = LocalDate.from(formatter.parse(this!!))!!
val String?.isDate: Boolean get() =
try {
    formatter.parse(this)
    true
} catch (e: Exception) {
    false
}

