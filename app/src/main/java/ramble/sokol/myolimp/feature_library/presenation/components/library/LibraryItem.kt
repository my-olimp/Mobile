package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun LibraryItem(
    type: String = "",
    subject: String = "",
    title: String = "",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .paint(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        // TODO change
                        .data("https://storage.yandexcloud.net/myolimp/subject/library/${subject.lowercase()}.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .build()
                ),
                contentScale = ContentScale.FillBounds
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (type != "") {
                LibrarySubtitleText(text = type)
            }
            if (subject != "") {
                Spacer(modifier = Modifier.height(4.dp))
                LibrarySubtitleText(text = subject)
            }
        }
        if (title != "") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = title,
                    style = mediumType(
                        color = White,
                        fontSize = 10.sp
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
