package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun TextFieldWithDropDown (
    previousData: String,
    label: String,
    listOfOptions: List<String>,
    onTextChanged: (String) -> Unit,
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var textValue by remember {
        mutableStateOf(previousData)
    }
    var size by remember {
        mutableStateOf(Size.Zero)
    }

    Box {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to the DropDown the same width
                    size = coordinates.size.toSize()
                },
            label = {
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
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueStart,
                focusedLabelColor = BlueStart,
                cursorColor = BlueStart,
                backgroundColor = White
            ),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    "contentDescription",
                    Modifier.clickable {
                        expanded = !expanded
                    }
                )
            },
            value = textValue,
            onValueChange = {
                textValue = it
                onTextChanged(it)
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(
                    with(
                        LocalDensity.current
                    )
                    {
                        size.width.toDp()
                    }
                )
        ) {
            listOfOptions.forEach { label ->
                DropdownMenuItem(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(size = 10.dp)
                        ),
                    onClick = {
                        textValue = label
                        onTextChanged(label)
                    },
                ) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            fontWeight = FontWeight(400),
                            color = SheetTitle,
                            letterSpacing = 0.3.sp,
                        ),
                    )
                }
            }
        }
    }
}
