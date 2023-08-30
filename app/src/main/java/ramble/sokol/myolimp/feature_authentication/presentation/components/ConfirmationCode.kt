package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationCode(
    modifier: Modifier,
    text: Int,
//    placeholder: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onTextChanged: (String) -> Unit,
) {

    var textValue by remember {
        mutableStateOf("")
    }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(size = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color(0xFFD8DADC),
            ),
            singleLine = true,
            maxLines = 1,
            value = textValue,
            onValueChange = {
                textValue = it
                onTextChanged(it)
            },
            textStyle = TextStyle(
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )

}
