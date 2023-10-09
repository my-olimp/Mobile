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
import androidx.compose.runtime.LaunchedEffect
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
import ramble.sokol.myolimp.destinations.LoginScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.SignUpViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.ConfirmationRow
import ramble.sokol.myolimp.feature_authentication.presentation.components.FooterAuth
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.MessageError
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent

@Destination
@Composable
fun CodeCheckerScreen (
    navigator: DestinationsNavigator,
    email: String,
    password: String
) {
    val viewModel = getViewModel<SignUpViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        // set previous data

        viewModel.onEvent(SignUpEvent.OnEmailUpdated(email = email))
        viewModel.onEvent(SignUpEvent.OnPasswordUpdated(password = password))
    })

//    OlimpTheme(
//        navigationBarColor = SecondaryScreen
//    ) {

        Column (
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

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = stringResource(R.string.email_confirmation),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = BlackProfile,
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = stringResource(R.string.email_description, state.value.email),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = BlackProfile,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.28.sp,
                    )
                )

                Spacer(modifier = Modifier.height(36.dp))

                ConfirmationRow(
                    onEvent = viewModel::onEvent,
                    isError = state.value.isCodeError,
                    navigator = navigator
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.value.isCodeError) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.error_register_message),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            fontWeight = FontWeight(400),
                            color = MessageError,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
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
//    }
}
