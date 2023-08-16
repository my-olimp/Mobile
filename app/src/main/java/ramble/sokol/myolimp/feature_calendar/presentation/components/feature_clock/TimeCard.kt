package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R

@Composable
fun TimeCard(
    modifier: Modifier,
    time: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val timeText =
        if (time == 0) "00"
        else if (time.toString().length == 1) "0$time"
        else time.toString()

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFC1D6FE),
        ),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            text = timeText,
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = if (isSelected) Color(0xE53579F8) else Color.White,
                letterSpacing = 0.28.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}
