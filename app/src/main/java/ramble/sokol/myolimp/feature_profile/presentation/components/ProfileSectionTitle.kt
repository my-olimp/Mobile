package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile

@Composable
fun ProfileSectionTitle (
    text: String,
    isShowEdit: Boolean = true,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(500),
                color = BlackProfile,
                letterSpacing = 0.4.sp,
            )
        )

        if (isShowEdit) {

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(onClick = onClick),
                painter = painterResource(id = R.drawable.ic_profile_edit),
                contentDescription = "edit personal data"
            )
        }
    }
}
