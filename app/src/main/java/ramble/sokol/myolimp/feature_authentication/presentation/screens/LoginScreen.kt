package ramble.sokol.myolimp.feature_authentication.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.OutlinedBtn
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextFieldAuthEmail
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextFieldAuthPassword
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextFieldAuthPhone
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.White

@RequiresApi(Build.VERSION_CODES.Q)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    OlimpTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_my_olimp),
                contentDescription = "image auth my olimp"
            )

            Spacer(modifier = Modifier.padding(top = 9.dp))

            Text(

                text = stringResource(id = R.string.login_to_service),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontWeight = FontWeight(400),
                    color = BlackProfile,
                    textAlign = TextAlign.Center,
                )
            )



        var tabIndex by remember {
            mutableIntStateOf(0)
        } // 1.
        val tabTitles = listOf("Почта", "Номер телефона")

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {

                TabRow(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    selectedTabIndex = tabIndex
                ) { // 3.
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index, // 4.
                            onClick = { tabIndex = index },
                            text = { Text(text = title,
                                color = Color.Black) }) // 5.
                    }
                }

            }

            when (tabIndex) { // 6.
                0 -> LoginEmailScreen()
                1 -> LoginPhoneScreen()
            }

            Spacer(modifier = Modifier.padding(top = 21.dp))

            FilledBtn(
                text = stringResource(id = R.string.login),
            ) {
                // for requset
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(15.dp),
                text = "Забыли пароль?",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                ),
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LoginEmailScreen() {
    OlimpTheme {
        val email by remember {
            mutableStateOf("")
        }
        val password by remember {
            mutableStateOf("")
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextFieldAuthEmail(
                previousData = email,
                label = stringResource(id = R.string.email),
                onTextChanged = {

                }
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            TextFieldAuthPassword(
                previousData = password,
                label = stringResource(id = R.string.password),
                onTextChanged = {

                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LoginPhoneScreen() {
    OlimpTheme {

        var phone by remember {
            mutableStateOf("")
        }
        val password by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextFieldAuthPhone(
                previousData = phone,
                label = stringResource(id = R.string.number_phone),
                onTextChanged = {
                    phone = it.take(10)
                }
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            TextFieldAuthPassword(
                previousData = password,
                label = stringResource(id = R.string.password),
                onTextChanged = {

                }
            )
        }
    }
}