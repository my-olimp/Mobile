package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.DisciplineColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Discipline (
    text: String,
    onClick: (String) -> Unit
) {

    var isSelected by remember {
        mutableStateOf(false)
    }

    FilterChip(
        onClick = {
//            isSelected = !isSelected
            onClick(text)
        },
        content = {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    letterSpacing = 0.24.sp,
                )
            )
        },
        selected = isSelected,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isSelected -> BlueStart
                else -> DisciplineColor
            }
        ),
        colors = ChipDefaults.filterChipColors(
            backgroundColor = DisciplineColor,
            contentColor = BlueStart,
        ),
    )
}
