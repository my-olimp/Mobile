package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.ShowError
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.MessageError
import ramble.sokol.myolimp.ui.theme.ProfileEditPlaceholder
import ramble.sokol.myolimp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarInput(
    label: String,
    previousData: String,
    isError: Boolean = false,
    errorText: String = "",
    onTextChanged: (String) -> Unit
) {

//    https://stackoverflow.com/questions/69064137/how-to-create-a-text-field-input-with-mask-in-jetpack-compose
//    colors
//    https://stackoverflow.com/questions/75544931/jetpack-compose-add-custom-dark-light-colors?noredirect=1&lq=1

    val calendarState = rememberSheetState()

    var textValue by remember {
        mutableStateOf(previousData)
    }

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date {
            textValue = it.toString()

            onTextChanged(textValue)
        },
        config = CalendarConfig (
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        ),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                calendarState.show()
            },
        trailingIcon = @Composable {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile_calendar),
                contentDescription = "calendar view"
            )
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
            errorBorderColor = MessageError,
            disabledTextColor = Color.Black,
            disabledTrailingIconColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = {
            textValue = it
            onTextChanged(it)
        },
        enabled = false,
        isError = isError
    )
    if(isError) {
        ShowError(text = errorText)
    }
}
