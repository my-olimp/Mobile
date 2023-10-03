package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.MainBackground
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType


@Composable
fun LibrarySubtitleText(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .background(
                color = MainBackground,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(horizontal = 2.dp, vertical = 2.dp),
        style = regularType(
            color = White,
            fontSize = 8.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
