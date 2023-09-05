package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowError(text : String) {
    Spacer(modifier = Modifier.height(4.dp))
    ErrorMessage(
        text = text
    )
}