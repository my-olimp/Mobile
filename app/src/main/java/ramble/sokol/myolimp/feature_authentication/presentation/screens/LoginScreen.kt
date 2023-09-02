package ramble.sokol.myolimp.feature_authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.RegisterInfoScreenDestination
import ramble.sokol.myolimp.destinations.SignUpScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.events.LoginEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.LoginViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.ErrorMessage
import ramble.sokol.myolimp.feature_authentication.presentation.components.FooterAuth
import ramble.sokol.myolimp.feature_authentication.presentation.components.PasswordField
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<LoginViewModel>()
    val state = viewModel.state.collectAsState()

    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column(
            modifier = Modifier
                .background(Transparent)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp),
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

                Spacer(modifier = Modifier.height(22.dp))

                OutlinedText(
                    previousData = "",
                    label = stringResource(id = R.string.email),
                    isError = state.value.isEmailError
                ) {
                    viewModel.onEvent(LoginEvent.OnEmailUpdated(it))
                }

                if (state.value.isEmailError) {
                    Spacer(modifier = Modifier.height(4.dp))

                    ErrorMessage(
                        text = stringResource(R.string.email_error_msg)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                PasswordField(
                    previousData = "",
                    label = stringResource(id = R.string.password),
                    isError = state.value.isPasswordError
                ) {
                    viewModel.onEvent(LoginEvent.OnPasswordUpdated(it))
                }

                if (state.value.isPasswordError) {
                    Spacer(modifier = Modifier.height(4.dp))

                    ErrorMessage(
                        text = stringResource(R.string.error_password_msg)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                FilledBtn(
                    padding = 0.dp,
                    isEnabled = state.value.isLogging,
                    text = stringResource(id = R.string.login),
                ) {
                    viewModel.onEvent(LoginEvent.OnLogin(navigator = navigator))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.forgot_password),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = BlackProfile,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SecondaryScreen)
                    .padding(horizontal = 54.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FooterAuth(
                    content = stringResource(R.string.not_registered),
                    offer = stringResource(R.string.register_account)
                ) {
                    //navigator.navigate(SignUpScreenDestination)
                    navigator.navigate(RegisterInfoScreenDestination)
                }
            }
        }
    }
}
