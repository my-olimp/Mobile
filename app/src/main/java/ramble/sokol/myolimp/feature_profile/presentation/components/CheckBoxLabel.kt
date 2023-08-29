package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun CheckBoxLabel (
    label: String,
    previousData: Boolean = false,
    onStatusChanged: (Boolean) -> Unit
) {

    var checkedState by remember {
        mutableStateOf(previousData)
    }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(4.dp))

        Checkbox(
            modifier = Modifier
                .size(16.dp),
            checked = checkedState,
            onCheckedChange = {
                checkedState = it
                onStatusChanged(it)
            },
            colors  = CheckboxDefaults.colors(
                checkmarkColor = White,
                checkedColor = BlueStart
            )
        )
        
        Spacer(modifier = Modifier.width(9.dp))

        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = SheetTitle,
                letterSpacing = 0.3.sp,
            )
        )
    }
}