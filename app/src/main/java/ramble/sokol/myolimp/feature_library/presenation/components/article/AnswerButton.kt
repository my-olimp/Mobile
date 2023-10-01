package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.DisabledBlue
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun AnswerBtn (
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(IntrinsicSize.Max)
        ,
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueStart,
            disabledContainerColor = DisabledBlue
        ),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = mediumType(
                color = White,
                fontSize = 13.sp,
                letterSpacing = 0.26.sp
            )
        )
    }
}