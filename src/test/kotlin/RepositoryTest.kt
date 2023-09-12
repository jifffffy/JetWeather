import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertTrue

internal class RepositoryTest {

    /***
     * This special client enable us to respond with a specified JSON String
     * called SUCCESS_RESPONSE.
     *
     * This JSON will then be parsed and transformed to something we expect, like a
     * WeatherResults class
     */
    private val mockClient = HttpClient(MockEngine) {
        engine {
            addHandler { _ ->
                respond(SUCCESS_RESPONSE, headers = responseHeaders)
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val repo = Repository(apiKey = "", client = mockClient)

    @Test
    fun `verify repository emits the correct result`() {
        runBlocking {
            val response = repo.weatherForCity("")

            assertThat(response).isInstanceOf(Lce.Content::class)

            val results = (response as Lce.Content).data

            assertThat(results.currentWeather.condition).isEqualTo("Overcast")
            assertThat(results.currentWeather.chanceOfRain).isNull()

            assertTrue { results.forecast.size == 3 }
        }
    }
}
