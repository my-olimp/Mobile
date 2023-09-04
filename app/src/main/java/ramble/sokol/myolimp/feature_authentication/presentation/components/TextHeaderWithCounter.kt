package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.SuccessStatus
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun TextHeaderWithCounter(
    headerText: String,
    counterText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = headerText,
            style = mediumType()
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = counterText,
            style = mediumType(
                color = SuccessStatus,
                fontSize = 14.sp
            )
        )
    }
}