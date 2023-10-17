package ramble.sokol.myolimp.utils.dimensions

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimension = compositionLocalOf { Dimension() }

data class Dimension(
    val default : Dp = 0.dp,
    val roundedCorner: Dp = 8.dp
)
