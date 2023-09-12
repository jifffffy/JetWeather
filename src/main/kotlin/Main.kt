import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.cdimascio.dotenv.dotenv

@Composable
@Preview
fun App() {
    val dotenv = dotenv()
    val repository = Repository(dotenv["WEATHER_API_KEY"])

    MaterialTheme {
        WeatherScreen(repository)
    }
}

fun main() = application {
    Window(
        title = "JetWeather",
        state = rememberWindowState(width = 800.dp, height = 700.dp),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
