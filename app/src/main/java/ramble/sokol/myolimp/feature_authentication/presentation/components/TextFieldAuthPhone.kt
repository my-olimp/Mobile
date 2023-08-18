package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun TextFieldAuthPhone (
    previousData: String,
    label: String,
    isEnabled: Boolean = true,
    onTextChanged: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf(previousData)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Row {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = ProfileEditPlaceholder,
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BlueStart,
            focusedLabelColor = BlueStart,
            cursorColor = BlueStart,
            backgroundColor = White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation("+0 (000) 000-00-00", '0'),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            if (it.length <= 11) textValue.value = it
        },
        enabled = isEnabled
    )
}