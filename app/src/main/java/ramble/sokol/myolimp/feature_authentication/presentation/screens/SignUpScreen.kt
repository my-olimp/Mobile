package ramble.sokol.myolimp.feature_authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.SignUpScreenDestination
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.OlimpTheme


@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator
) {
    //SignUpScreen1()
    SignUpScreenData3()
}


@Destination
@Composable
fun SignUpScreen1() {
    OlimpTheme {
        var tabIndex by remember { mutableStateOf(0) } // 1.
        val tabTitles = listOf("Почта", "Номер телефона")
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(0.05184.dp)
                    .width(100.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "image description",
                //contentScale = ContentScale.None
            )
            Text(
                modifier = Modifier
                    .width(220.dp)
                    .height(40.dp),
                text = "Для создания учетной записи укажите свои данные:",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    //fontFamily = FontFamily(Font(R.font.)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Center,
                )
            )
            TabRow(selectedTabIndex = tabIndex) { // 3.
                tabTitles.forEachIndexed { index, title ->
                    Tab(selected = tabIndex == index, // 4.
                        onClick = { tabIndex = index },
                        text = { Text(text = title) }) // 5.
                }
            }
            when (tabIndex) { // 6.
                0 -> SignUpEmailScreen()
                1 -> SignUpPhoneScreen()
            }


        }
    }
}

@Destination
@Composable
fun SignUpEmailScreen() {
    OlimpTheme {
        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var checked_password by remember { mutableStateOf("") }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(15.dp),
                text = "Почта",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier
                    .width(331.dp)
                    .height(45.dp),
                value = name,
                singleLine = true,
                onValueChange = { value -> name = value },
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(15.dp),
                text = "Пароль",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier
                    .width(331.dp)
                    .height(45.dp),
                value = password,
                singleLine = true,
                onValueChange = { value -> password = value },
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(15.dp),
                text = "Подтверждение пароля",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier
                    .background(color = Color.White)
                    .width(331.dp)
                    .height(45.dp),
                value = checked_password,
                singleLine = true,
                onValueChange = { value -> checked_password = value },
            )
            Spacer(modifier = Modifier.height(16.dp))

            val mainButtonColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8CB2FB),
                contentColor = MaterialTheme.colorScheme.surface
            )
            Button(colors = mainButtonColor, onClick = {}, modifier = Modifier
                .width(328.dp)
                .height(49.dp)) {
                Text(text = "Зарегистрироваться", fontSize = 18.sp)
            }
        }
    }
}

@Destination
@Composable
fun SignUpPhoneScreen() {
    OlimpTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}


@Destination
@Composable
fun SignUpScreenPhone2() {
    OlimpTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(0.05184.dp)
                    .width(100.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "image description",
                //contentScale = ContentScale.None
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .width(195.dp)
                    .height(21.dp),
                text = "Персональные данные",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xD91A0710),
                    letterSpacing = 0.36.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(37.dp)
                    .height(17.dp),
                text = "1 из 4",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF6BC62D),
                    letterSpacing = 0.28.sp,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(16.dp),
                text = "ФИО *",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                )
            )
        }
    }
}


@Destination
@Composable
fun SignUpScreenEmail2() {
    OlimpTheme {
        // Подтверждение почты
    }
}


@Destination
@Composable
fun SignUpScreenData1() {
    OlimpTheme {
        // Персональные данные
    }
}


@Destination
@Composable
fun SignUpScreenData2() {
    OlimpTheme {
        // Образование
    }
}


@Destination
@Composable
fun SignUpScreenData3() {
    var findvalue by remember { mutableStateOf("") }
    OlimpTheme {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(0.05184.dp)
                    .width(100.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "image description",
                //contentScale = ContentScale.None
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "Дисциплины",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xD91A0710),
                        letterSpacing = 0.36.sp,
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "3 из 4",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF6BC62D),
                        letterSpacing = 0.28.sp,
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(20.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.iconsearch),
                    contentDescription = "image description",
                )

                TextField(
                    modifier = Modifier
                        .width(331.dp)
                        .height(45.dp),
                    value = findvalue,
                    singleLine = true,
                    onValueChange = { value -> findvalue = value },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Выберите или введите в поиск предметы, к которым готовите",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xA61A0710),
                    letterSpacing = 0.22.sp,
                )
            )

            Text(
                text = "Популярные дисциплины",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xD91A0710),
                    letterSpacing = 0.32.sp,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            FilledBtn(
                text = "Дальше"
            ) {
                //navigator
            }
        }
    }
}


@Destination
@Composable
fun SignUpScreenData4() {
    var snils by remember { mutableStateOf("") }
    OlimpTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(0.05184.dp)
                    .width(100.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "image description",
                //contentScale = ContentScale.None
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier
                        .width(146.dp)
                        .height(21.dp),
                    text = "Фото",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xD91A0710),
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    modifier = Modifier
                        .width(40.dp)
                        .height(17.dp),
                    text = "4 из 4",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF6BC62D),
                        letterSpacing = 0.28.sp,
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                painter = painterResource(id = R.drawable.ic_default_img),
                contentDescription = "user image"
            )

            Spacer(modifier = Modifier.height(16.dp))
            FilledBtn(
                text = "Дальше"
            ) {
                //navigator
            }
        }
        /*Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                modifier = Modifier
                    .width(146.dp)
                    .height(21.dp),
                text = "Фото и документ",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xD91A0710),
                    letterSpacing = 0.36.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(40.dp)
                    .height(17.dp),
                text = "4 из 4",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF6BC62D),
                    letterSpacing = 0.28.sp,
                )
            )
        }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(320.dp)
                    .height(16.dp),
                text = "СНИЛС *",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                    letterSpacing = 0.26.sp,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier
                    .width(331.dp)
                    .height(45.dp),
                value = snils,
                singleLine = true,
                onValueChange = { value -> snils = value },
            )

        }
        Row(
            modifier = Modifier
                .width(328.dp)
                .height(49.dp)
                .background(color = Color(0xFF8CB2FB), shape = RoundedCornerShape(size = 15.dp))
                .padding(start = 123.dp, top = 14.dp, end = 123.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .width(67.dp)
                    .height(21.dp),
                text = "Дальше",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )
        }*/
    }
}


/*@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}*/