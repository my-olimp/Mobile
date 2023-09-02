package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.ui.theme.DisabledBlue
import ramble.sokol.myolimp.ui.theme.MainPageBlue

@Composable
fun RadioText(
    header: String = "",
    onClick: () -> Unit,
    selected: Boolean = true,
    textStyle: TextStyle = TextStyle.Default
) {
    Row (
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier
                .height(14.dp)
                .width(14.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = MainPageBlue,
                unselectedColor = DisabledBlue
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = header,
            style = textStyle
        )
    }
}