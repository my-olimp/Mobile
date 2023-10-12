package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.GreyProfileAchievement

@Composable
fun CommentText (
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.regular)),
            fontWeight = FontWeight(400),
            color = GreyProfileAchievement,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp,
        )
    )
}
