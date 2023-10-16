package ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.SendCodeScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.events.SendCodeEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.SendCodeViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.MinuteTimerText
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.Transparent
import ramble.sokol.myolimp.ui.theme.mediumType

@[Composable Destination]
fun SendCodeScreen(
    navigator: DestinationsNavigator,
    email: String,
    isRegistering: Boolean = false
) {

    val viewModel = getViewModel<SendCodeViewModel>()
    val state = viewModel.state.collectAsState()
    val remainTime by rememberUpdatedState(newValue = state.value.timer)

    OlimpTheme(
        navigationBarColor = SecondaryScreen,
        isLoading = state.value.isLoading,
        isError = state.value.isNetworkError,
        onReload = { navigator.navigate(SendCodeScreenDestination(email,isRegistering)) },
        content = {
            Column(
                modifier = Modifier
                    .background(Transparent)
                    .fillMaxSize()
                    .blur(if (state.value.isLoading || state.value.isNetworkError) 4.dp else 0.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.auth_my_olimp),
                        contentDescription = "image auth my olimp"
                    )
                    VerticalSpacer(height = 64.dp)

                    Image(
                        painter = painterResource(id = R.drawable.ic_send_code),
                        contentDescription = "vector send code",
                        modifier = Modifier
                            .fillMaxHeight(0.4F)
                            .fillMaxWidth()
                    )

                    VerticalSpacer(height = 32.dp)

                    Text(
                        text = if(isRegistering) stringResource(id = R.string.check_email_registraion_end_code_send)
                        else stringResource(id = R.string.check_email_code_send),
                        style = mediumType(
                            color = SheetTitle,
                            letterSpacing = 0.36.sp,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(horizontal = 32.dp)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MinuteTimerText(
                        remainTime = remainTime,
                        onTick = { viewModel.onEvent(SendCodeEvent.OnUpdateTimer(it)) }
                    ) {
                       viewModel.onEvent(SendCodeEvent.OnResendCode(navigator, email, isRegistering))
                    }
                }
            }
        }
    )
}