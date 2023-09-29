package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.GreyProfileData

@Composable
fun ProfileSectionContent (
    title: String,
    content: String = ""
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(500),
                color = GreyProfileData,
                letterSpacing = 0.3.sp,
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = content,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = BlackProfile,
                letterSpacing = 0.3.sp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
