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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterEducationViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextHeaderWithCounter
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.EditableDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent

@Preview(showBackground = true)
@Composable
fun RegisterEducationScreenPreview() {
    RegisterEducationScreen()
}

@Destination
@Composable
fun RegisterEducationScreen() {

    val viewModel = getViewModel<RegisterEducationViewModel>()

    val state = viewModel.state.collectAsState()

    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Transparent),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.auth_my_olimp),
                    contentDescription = "image auth my olimp"
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextHeaderWithCounter(
                    headerText = stringResource(id = R.string.education),
                    counterText = stringResource(id = R.string.two_of_four)
                )

                Spacer(modifier = Modifier.height(16.dp))


                EditableDropDown(
                    previousData = state.value.region,
                    label = stringResource(id = R.string.region),
                    options = stringArrayResource(id = R.array.region).toList(),
                ) {
                    viewModel.onEvent(RegistrationEvent.OnRegionChanged(it))
                }

                Spacer(modifier = Modifier.height(12.dp))

                EditableDropDown(
                    previousData = state.value.city,
                    label = stringResource(id = R.string.city_profile),
                    options = stringArrayResource(id = R.array.city).toList()
                ) {
                    viewModel.onEvent(RegistrationEvent.OnCityChanged(it))
                }

                Spacer(modifier = Modifier.height(12.dp))

                EditableDropDown(
                    previousData = state.value.school,
                    label = stringResource(id = R.string.school),
                    options = stringArrayResource(id = R.array.school).toList()
                ) {
                    viewModel.onEvent(RegistrationEvent.OnSchoolChanged(it))
                }

                Spacer(modifier = Modifier.height(12.dp))

                ReadOnlyDropDown(
                    options = stringArrayResource(id = R.array.grade).toList(),
                    previousData = state.value.grade,
                    label = stringResource(id = R.string.grade)
                ) {
                    viewModel.onEvent(RegistrationEvent.OnGradeChanged(it))
                }

                Spacer(modifier = Modifier.height(24.dp))

                FilledBtn(
                    text = stringResource(id = R.string.further),
                    padding = 0.dp
                ) {
                    //viewModel.onEvent(RegistrationEvent.OnNext())
                }
            }
        }


    }
}

