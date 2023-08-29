package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.MessageError
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun OutlinedText (
    previousData: String,
    label: String,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    isEmail: Boolean = false,
    onTextChanged: (String) -> Unit,
) {

    var textValue by remember {
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

                Text(
                    text = stringResource(R.string.star),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = if (isError) MessageError else BlueStart,
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BlueStart,
            focusedLabelColor = BlueStart,
            cursorColor = BlueStart,
            backgroundColor = White,
            errorBorderColor = MessageError
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Next,
            keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = {
            textValue = it
            onTextChanged(it)
        },
        isError = isError,
        enabled = isEnabled
    )
}
