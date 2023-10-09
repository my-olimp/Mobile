package ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.domain.events.SendCodeEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.SendCodeViewModel
import ramble.sokol.myolimp.feature_profile.presentation.components.ErrorString
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent
import ramble.sokol.myolimp.ui.theme.regularType



@[Composable Destination]
fun PassEmailScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<SendCodeViewModel>()
    val state = viewModel.state.collectAsState()

    OlimpTheme(
        navigationBarColor = SecondaryScreen,
        onReload = {},
        content = {

            Column(
                modifier = Modifier
                    .background(Transparent)
                    .fillMaxSize(),
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
                    VerticalSpacer(height = 8.dp)

                    Text(
                        text = stringResource(id = R.string.pass_email_to_restore_password),
                        style = regularType(color = BlackProfile),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 50.dp)
                    )

                    VerticalSpacer(height = 14.dp)

                    OutlinedText(
                        previousData = state.value.email,
                        label = stringResource(id = R.string.email),
                        isError = state.value.emailError,
                        errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.email_error)
                    ) {
                        viewModel.onEvent(SendCodeEvent.OnEmailTyped(it))
                    }

                    VerticalSpacer(height = 33.dp)

                    FilledBtn(
                        text = stringResource(id = R.string.next),
                        padding = 0.dp
                    ) {
                        viewModel.onEvent(SendCodeEvent.OnContinue(navigator))
                    }
                }
            }
        }
    )
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}