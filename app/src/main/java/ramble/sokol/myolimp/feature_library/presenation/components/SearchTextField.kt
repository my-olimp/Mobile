package ramble.sokol.myolimp.feature_library.presenation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun SearchTextField (
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onCancelSearching: () -> Unit,
) {

    val textValue = remember {
        mutableStateOf("")
    }

    var hasFocus by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                hasFocus = it.isFocused
            },
        shape = RoundedCornerShape(12.dp),
        label = {
            Row {
                Text(
                    text = stringResource(R.string.search),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = if (hasFocus) BlueStart else GreyProfileData,
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

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                tint = if (hasFocus) BlueStart else GreyProfileData,
                painter = painterResource(id = R.drawable.ic_calendar_search),
                contentDescription = "search"
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable {
                        textValue.value = ""
                        focusManager.clearFocus()
                        onCancelSearching()
                    },
                tint = if (hasFocus) BlueStart else GreyProfileData,
                painter = painterResource(id = R.drawable.ic_profile_cancel),
                contentDescription = "search"
            )
        }
    )
}
