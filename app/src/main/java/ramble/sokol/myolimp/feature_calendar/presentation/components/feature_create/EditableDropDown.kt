package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.ShowError
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.MessageError
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun EditableDropDown(
    previousData: String,
    label: String,
    options: List<String>,
    isError: Boolean = false,
    errorText: String = stringResource(id = R.string.null_textfield_error,"none"),
    onTextChanged: (String) -> Unit,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var textValue by remember {
        mutableStateOf(previousData)
    }

    var dropDownOptions by remember {
        mutableStateOf(options)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) expanded = false
                },
            value = textValue,
            onValueChange = { value->
                expanded = true
                textValue = value

                // show options
                dropDownOptions = options.filter {
                    it.contains(value, ignoreCase = true)
                }

                onTextChanged(value)
            },
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
                            color = BlueStart,
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
            trailingIcon = {

                Icon (
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .clickable {
                            expanded = !expanded
                            dropDownOptions = options
                        }
                        .rotate(
                            if (expanded) 180f else 0f
                        ),
                    painter = painterResource(
                        id = R.drawable.ic_profile_arrow_drop_down
                    ),
                    contentDescription = "open icon"
                )
            },

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            maxLines = 1,
            isError = isError
        )

        // filter options based on text field value
        DropdownMenu(
            modifier = Modifier
                .background(White)
                .heightIn(
                    max = 150.dp
                ),
            expanded = expanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                expanded = false
            },
        ) {
            dropDownOptions.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(text = value)
                    },
                    onClick = {
                        expanded = false
                        textValue = value

                        dropDownOptions = options.filter {
                            it.contains(value, ignoreCase = true)
                        }

                        onTextChanged(value)
                    }
                )
            }
        }
    }
    if (isError) ShowError(
        text = errorText
    )
}
