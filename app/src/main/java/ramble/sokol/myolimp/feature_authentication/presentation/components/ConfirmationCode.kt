package ramble.sokol.myolimp.feature_authentication.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.domain.utils.onlyNumbers
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.MessageError
import ramble.sokol.myolimp.ui.theme.OutlinedUnFocusedBorder
import ramble.sokol.myolimp.ui.theme.SheetTitle

@Composable
fun ConfirmationCode(
    modifier: Modifier,
    placeholder: String,
    isError: Boolean,
    onTextChanged: (Int?) -> Unit,
) {

    var textValue by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BlueStart,
            unfocusedBorderColor = OutlinedUnFocusedBorder,
            cursorColor = BlueStart,
            errorBorderColor = MessageError
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    fontSize = 34.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = SheetTitle,
                    textAlign = TextAlign.Center
                )
            )
        },
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = {
            // only numbers
            if (it.onlyNumbers()) {
                try {
                    textValue = textValue.replace(
                        textValue,
                        if (it.indexOf(textValue) == 0 && it.length > 1) it[1].toString() else it[0].toString()
                    )

                    onTextChanged(
                        textValue.toInt()
                    )
                } catch (_: Exception) {
                    textValue = ""

                    onTextChanged(
                        null
                    )
                }
            }
        },
        textStyle = TextStyle(
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(500),
            color = SheetTitle,
            textAlign = TextAlign.Center
        ),
        isError = isError
    )
}
