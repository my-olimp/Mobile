package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.ShowError
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.MessageError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadOnlyDropDown (
    options: List<String>,
    previousData: String,
    label: String,
    isError: Boolean = false,
    errorText: String = stringResource(id = R.string.null_textfield_error,"none"),
    onTextChanged: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var textValue by remember {
        mutableStateOf(previousData)
    }

    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {

        // The `menuAnchor` modifier must be passed to the text field for correctness.

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = {
                Row {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0x80222222),
                            letterSpacing = 0.3.sp,
                        )
                    )

                    Text(
                        text = stringResource(R.string.star),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = BlueStart,
                            letterSpacing = 0.3.sp,
                        )
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF3579F8),
                focusedLabelColor = Color(0xFF3579F8),
                cursorColor = Color(0xFF3579F8),
                backgroundColor = White,
                errorBorderColor = MessageError
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            singleLine = true,
            maxLines = 1,
            value = textValue,
            onValueChange = {
                // user can't write anything
            },
            readOnly = true,
            isError = isError
        )

        ExposedDropdownMenu(
            modifier = Modifier
                .background(White)
                .heightIn(
                    max = 150.dp
                ),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(selectionOption)
                    },
                    onClick = {
                        textValue = selectionOption
                        expanded = false
                        onTextChanged(selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    if(isError) {
        ShowError(text = errorText)
    }
}
