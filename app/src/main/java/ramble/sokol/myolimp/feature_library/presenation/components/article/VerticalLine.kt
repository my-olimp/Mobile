package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.ui.theme.BlueStart

@Composable
fun VerticalLine(
    width: Dp = 2.dp
) {
    Spacer(
        modifier = Modifier
            .background(BlueStart)
            .width(width)
            .fillMaxHeight()
    )
}