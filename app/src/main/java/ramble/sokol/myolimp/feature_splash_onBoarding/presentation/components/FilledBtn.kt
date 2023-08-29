package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.DisabledBlue
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun FilledBtn (
    text: String,
    isEnabled: Boolean = true,
    padding: Dp = 36.dp,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding),
        shape = RoundedCornerShape(size = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueStart,
            disabledContainerColor = DisabledBlue
        ),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(600),
                color = White,
                textAlign = TextAlign.Center
            )
        )
    }
}
