
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ramble.sokol.myolimp.destinations.LoginScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.SignUpViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.ErrorMessage
import ramble.sokol.myolimp.feature_authentication.presentation.components.FooterAuth
import ramble.sokol.myolimp.feature_authentication.presentation.components.PasswordField
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BackgroundProgressIndicator
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.ErrorStatus
import ramble.sokol.myolimp.ui.theme.MiddleStatus
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.SuccessStatus
import ramble.sokol.myolimp.ui.theme.Transparent


@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<SignUpViewModel>()
    val state = viewModel.state.collectAsState()

    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column (
            modifier = Modifier
                .background(Transparent)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
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
                    viewModel.onEvent(SignUpEvent.OnEmailUpdated(it))
                }

                if (state.value.isEmailError) {
                    Spacer(modifier = Modifier.height(4.dp))

                    ErrorMessage(
                        text = stringResource(R.string.email_error_already_in_use)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                PasswordField(
                    previousData = "",
                    label = stringResource(id = R.string.password),
                ) {
                    viewModel.onEvent(SignUpEvent.OnPasswordUpdated(it))
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp)),
                    progress = state.value.passwordStatus,
                    trackColor = BackgroundProgressIndicator,
                    color = when (state.value.passwordStatus) {
                        1f -> SuccessStatus
                        0.3f -> ErrorStatus
                        0.6f -> MiddleStatus
                        else -> BackgroundProgressIndicator
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    previousData = "",
                    label = stringResource(id = R.string.confirm_password)
                ) {
                    viewModel.onEvent(SignUpEvent.OnConfirmedPasswordUpdated(it))
                }

                if (state.value.passwordError != null) {
                    Spacer(modifier = Modifier.height(4.dp))

                    ErrorMessage(
                        text = state.value.passwordError!!
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                FilledBtn(
                    padding = 0.dp,
                    isEnabled = state.value.isRegistering,
                    text = stringResource(id = R.string.register),
                ) {
                    viewModel.onEvent(SignUpEvent.OnSignUp(navigator = navigator))
                }

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.poilitics),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = BlackProfile,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SecondaryScreen)
                    .padding(horizontal = 54.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FooterAuth(
                    content = stringResource(R.string.already_registered),
                    offer = stringResource(R.string.login_to_account)
                ) {
                    navigator.navigate(LoginScreenDestination)
                }
            }
        }
    }
}
