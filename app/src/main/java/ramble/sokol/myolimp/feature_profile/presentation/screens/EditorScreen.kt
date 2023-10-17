package ramble.sokol.myolimp.feature_profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ResultEditorScreenDestination
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White

@Destination
@Composable
fun EditorScreen(
    navController: NavController
) {
    OlimpTheme(
        onReload = {

        }
    ) {

        var text by remember {
            mutableStateOf("")
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
                    .background(
                        White,
                        RoundedCornerShape(16.dp)
                    ),
                placeholder = {
                   Text(
                       text = "Напишите сюда текст",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(400),
                            color = SheetTitle,

                            letterSpacing = 0.3.sp,
                        )
                   )
                },
                shape = RoundedCornerShape(5.dp),
                value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        navController.navigate(ResultEditorScreenDestination(text))
                    }
                ),
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(400),
                    color = SheetTitle,

                    letterSpacing = 0.3.sp,
                )
            )
        }
    }
}