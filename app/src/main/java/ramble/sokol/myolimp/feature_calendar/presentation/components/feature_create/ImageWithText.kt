package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.GreyProfileAchievement

@Composable
fun ImageWithText (
    drawable: Int,
    text: String
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = drawable),
            contentDescription = "no plans",
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

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
}
