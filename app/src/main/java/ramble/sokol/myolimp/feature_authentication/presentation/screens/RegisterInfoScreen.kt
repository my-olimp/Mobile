package ramble.sokol.myolimp.feature_authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterInfoViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.ErrorMessage
import ramble.sokol.myolimp.feature_authentication.presentation.components.RadioText
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextHeaderWithCounter
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.components.CalendarInput
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent
import ramble.sokol.myolimp.ui.theme.regularType


@Destination("temp")
@Composable
fun RegisterInfoScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<RegisterInfoViewModel>()

    val state = viewModel.state.collectAsState()

    val isSelected = remember {
        mutableStateOf(true)
    }

    val activityType = stringArrayResource(id = R.array.activity_type)

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

                Spacer(modifier = Modifier.padding(top = 16.dp))

                TextHeaderWithCounter(
                    headerText = stringResource(id = R.string.individual_data),
                    counterText = stringResource(id = R.string.one_of_four)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedText(
                    previousData = state.value.fio,
                    label = stringResource(id = R.string.name_surname),
                    isEnabled = true,
                    onTextChanged = {
                        viewModel.onEvent(
                            RegistrationEvent.OnNameSurnameChanged(it)
                        )
                    },
                    isError = state.value.fioError
                )

                if (state.value.fioError) {
                    Spacer(modifier = Modifier.height(4.dp))

                    ErrorMessage(
                        text = stringResource(R.string.fio_error)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = stringResource(id = R.string.gender) + ':',
                        style = regularType(color = GreyProfileData)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    RadioText(
                        header = stringResource(id = R.string.female_gender),
                        textStyle = regularType(),
                        selected = isSelected.value,
                        onClick = {
                            isSelected.value = true
                            viewModel.onEvent(
                                event = RegistrationEvent.OnGenderChanged("f")
                            )
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    RadioText(
                        header = stringResource(id = R.string.male_gender),
                        textStyle = regularType(),
                        selected = !(isSelected.value),
                        onClick = {
                            isSelected.value = false
                            viewModel.onEvent(
                                event = RegistrationEvent.OnGenderChanged("m")
                            )
                        },
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                CalendarInput(
                    label = stringResource(id = R.string.dob),
                    previousData = state.value.bdate
                ) {
                    viewModel.onEvent(RegistrationEvent.OnDobChanged(it))
                }

                Spacer(modifier = Modifier.height(12.dp))

                ReadOnlyDropDown(
                    options =  activityType.toList(),
                    previousData = state.value.activityType,
                    label = stringResource(id = R.string.type_of_activity)
                ) {
                    viewModel.onEvent(RegistrationEvent.OnActivityTypeChanged(
                        activityType = if(it == activityType[0]) "s" else "t"
                    ))
                }

                Spacer(modifier = Modifier.height(24.dp))

                FilledBtn(
                    text = stringResource(id = R.string.further),
                    padding = 0.dp
                ) {
                    viewModel.onEvent(RegistrationEvent.OnNext(navigator))
                }
                
            }
        }
    }
}
