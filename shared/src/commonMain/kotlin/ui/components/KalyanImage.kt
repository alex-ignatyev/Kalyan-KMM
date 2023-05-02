package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.intercept.Interceptor
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageRequestEvent
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.model.NullRequestData
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun KalyanImage(
    url: String,
    modifier: Modifier = Modifier,
    size: Int = 48,
    blurRadius: Int = 0,
) {
    Box(contentAlignment = Alignment.Center) {
        val request = remember(url, blurRadius) {
            ImageRequest {
                data(url)
                addInterceptor(NullDataInterceptor)
                if (blurRadius > 0) {
                   // blur(blurRadius)
                }
            }
        }

        val painter = rememberAsyncImagePainter(request)

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.size(size.dp),
        )

        when (val requestState = painter.requestState) {
            is ImageRequestState.Loading -> {
                val event = requestState.event
                if (event is ImageRequestEvent.ReadDiskCache && !event.hasCache) {
                    KalyanCircularProgress()
                }
            }

            is ImageRequestState.Failure -> {
                Text(requestState.error.message ?: "Error")
            }

            ImageRequestState.Success -> Unit
        }
    }
}

/**
 * return empty painter if request is null or empty
 */
object NullDataInterceptor : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        if (data === NullRequestData || data is String && data.isEmpty()) {
            return ImageResult.Painter(
                request = chain.request,
                painter = EmptyPainter,
            )
        }
        return chain.proceed(chain.request)
    }

    private object EmptyPainter : Painter() {
        override val intrinsicSize: Size get() = Size.Unspecified
        override fun DrawScope.onDraw() {}
    }
}
