package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R

// numbers
@Composable
fun Mark(
    text: String,
    index: Int, // 0..23
    onIndex: (Int) -> Unit,
    isSelected: Boolean
) {

    Text(
        modifier = Modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = {
                    onIndex(index)
                }
            ),
        text = text,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.regular)),
            fontWeight = FontWeight(400),
            color = if (isSelected) Color.White else Color(0xFF757575),
            letterSpacing = 0.28.sp,
        )
    )
}
