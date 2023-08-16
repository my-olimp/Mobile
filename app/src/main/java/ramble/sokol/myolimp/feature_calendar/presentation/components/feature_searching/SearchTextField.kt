package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState

@Composable
fun SearchTextField (
    state: PlanState,
    onClearFocus: () -> Unit,
    onTextChanged: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }

    var hasFocus by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    if (!state.isSearching && !state.isAddingPlan) {
        focusManager.clearFocus()
    }

    OutlinedTextField(
        modifier = Modifier
            .onFocusChanged {
                hasFocus = it.isFocused
            },
        shape = RoundedCornerShape(12.dp),
        label = {
            Row {
                Text(
                    text = "Найти",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = if (hasFocus) Color(0xFF3579F8) else Color(0xFF757575),
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF3579F8),
            focusedLabelColor = Color(0xFF3579F8),
            cursorColor = Color(0xFF3579F8),
            backgroundColor = Color.White,

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onClearFocus()
            focusManager.clearFocus()
        }),
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
                tint = if (hasFocus) Color(0xFF3579F8) else Color(0xFF757575),
                painter = painterResource(id = R.drawable.ic_calendar_search),
                contentDescription = "search"
            )
        }
    )
}
