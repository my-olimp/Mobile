package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.ui.theme.LibraryLine

@Composable
fun HorizontalLine(
    height: Dp = 1.dp
) {
    Spacer(
        modifier = Modifier
            .background(color = LibraryLine)
            .height(height)
            .fillMaxWidth()
    )
}