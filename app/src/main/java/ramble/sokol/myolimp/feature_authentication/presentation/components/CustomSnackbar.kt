package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ramble.sokol.myolimp.ui.theme.DisabledBlue
import ramble.sokol.myolimp.ui.theme.Transparent

@Composable
fun CustomSnackbar (
    text: String,
    onAction: () -> Unit
) {

    Snackbar (
        modifier = Modifier
            .fillMaxWidth(),

        elevation = 0.dp,
        backgroundColor = Transparent,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(BlueStart)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = BlackProfile,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.36.sp,
                )
            )

            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(DisabledBlue),
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                    )
                },
                onClick = {
                    onAction()
                }
            )
        }
    }
}
