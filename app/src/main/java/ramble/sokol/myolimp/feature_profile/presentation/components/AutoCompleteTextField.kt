package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.White

/*
* Deprecated
*/
@Composable
fun AutoCompleteTextField(
    previousData: String,
    label: String,
    listOfOptions: List<String>,
    onTextChanged: (String) -> Unit,
) {

    var textValue by  remember {
        mutableStateOf(previousData)
    }

    val dropDownExpanded =  remember {
        mutableStateOf(false)
    }

    val dropDownOptions = remember {
        mutableStateOf(listOfOptions.take(3))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) dropDownExpanded.value = false
                },
            value = textValue,
            onValueChange = { value->

                dropDownExpanded.value = true
                textValue = value
                dropDownOptions.value = listOfOptions.filter {
                    it.lowercase().startsWith(value.lowercase()) && it != value
                }.take(3)

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
                backgroundColor = White
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile_arrow_drop_down),
                    "contentDescription",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            dropDownExpanded.value = !dropDownExpanded.value
                        }
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            maxLines = 1,
        )
        DropdownMenu(
            expanded = dropDownExpanded.value,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                dropDownExpanded.value = false
            }
        ) {
            dropDownOptions.value.forEach { value ->
                DropdownMenuItem(onClick = {

                    dropDownExpanded.value = true
                    textValue = value
                    dropDownOptions.value = listOfOptions.filter {
                        it.lowercase().startsWith(value.lowercase()) && it != value
                    }.take(3)

                    onTextChanged(value)
                }) {
                    Text(text = value)
                }
            }
        }
    }
}
