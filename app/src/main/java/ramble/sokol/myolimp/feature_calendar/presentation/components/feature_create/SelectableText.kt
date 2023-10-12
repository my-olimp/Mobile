package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.ui.theme.CalendarFocusedText
import ramble.sokol.myolimp.ui.theme.CalendarSelectableText
import ramble.sokol.myolimp.ui.theme.CalendarUnFocusedText
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun SelectableText (
    state: PlanState,
    text: String,
    onTextClick: (String) -> Unit
){

    Box(
        modifier = Modifier
            .background(
                color = if (state.type == text) CalendarSelectableText else White,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(14.dp)
            .clip(CircleShape)
            .clickable {
                onTextClick(text)
            }
    ) {
        Text (
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = if (state.type == text) CalendarFocusedText else CalendarUnFocusedText,
                letterSpacing = 0.28.sp,
            )
        )
    }
}
