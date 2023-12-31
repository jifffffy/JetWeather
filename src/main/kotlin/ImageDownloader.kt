import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import org.jetbrains.skia.Image

object ImageDownloader {
    private val imageClient = HttpClient(CIO)

    suspend fun downloadImage(url: String): ImageBitmap {
        val image = imageClient.get(url).body<ByteArray>()
        return Image.makeFromEncoded(image).toComposeImageBitmap()
    }
}