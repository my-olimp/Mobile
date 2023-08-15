package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import java.util.Calendar

@Composable
fun TimeInput(
    modifier: Modifier = Modifier,
    label: String,
    onTextChanged: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val context = LocalContext.current

    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            time.value = "$hour:$minute"
        }, hour, minute, false
    )

    OutlinedTextField(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                timePickerDialog.show()
            },
        trailingIcon = @Composable {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_time),
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
                        color = Color(0xFF6D7885),
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF3579F8),
            focusedLabelColor = Color(0xFF3579F8),
            cursorColor = Color(0xFF3579F8),
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it

            onTextChanged(it)
        },
        enabled = false
    )
}
