package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart

@Composable
fun FooterAuth (
    content: String,
    offer: String,
    onUrlClicked: () -> Unit
) {
    Text(
        text = content,
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 21.sp,
            fontFamily = FontFamily(Font(R.font.regular)),
            fontWeight = FontWeight(400),
            color = BlackProfile,
            textAlign = TextAlign.Center
        )
    )

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onUrlClicked()
            },
        text = offer,
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 21.sp,
            fontFamily = FontFamily(Font(R.font.regular)),
            fontWeight = FontWeight(400),
            color = BlueStart,
        )
    )
}
