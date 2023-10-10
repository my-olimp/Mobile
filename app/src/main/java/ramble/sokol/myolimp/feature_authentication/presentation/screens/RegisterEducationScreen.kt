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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.RegisterEducationScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.feature_authentication.data.models.toListString
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEducationEvent
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterEducationViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextHeaderWithCounter
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.EditableDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.components.ErrorString
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent


@Destination
@Composable
fun RegisterEducationScreen(
    navigator: DestinationsNavigator,
    isWorkScreen: Boolean = false
) {

    val viewModel = getViewModel<RegisterEducationViewModel>()

    val state = viewModel.state.collectAsState()

    OlimpTheme(
        isLoading = state.value.isLoading,
        navigationBarColor = SecondaryScreen,
        onReload = {
            navigator.navigate(RegisterEducationScreenDestination())
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Transparent)
                    .blur(if (state.value.isLoading) 4.dp else 0.dp),
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
                        headerText = if (!isWorkScreen) stringResource(id = R.string.education)
                        else stringResource(id = R.string.work),
                        counterText = stringResource(id = R.string.two_of_four)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    EditableDropDown(
                        previousData = state.value.region.name,
                        label = stringResource(id = R.string.region),
                        options = state.value.regionList.toListString(),
                        isError = state.value.regionError,
                        errorText = ErrorString(id = R.string.null_textfield_error,addId = R.string.region)
                    ) { newRegion ->
                        viewModel.onEvent(RegistrationEducationEvent.OnRegionChanged(
                            state.value.regionList.find { it.name == newRegion } ?: Region()
                        ))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    EditableDropDown(
                        previousData = state.value.city.name,
                        label = stringResource(id = R.string.city_profile),
                        options = state.value.cityList.toListString(),
                        isError = state.value.cityError,
                        errorText = ErrorString(id = R.string.null_textfield_error,addId = R.string.city)
                    ) { newCity ->
                        viewModel.onEvent(RegistrationEducationEvent.OnCityChanged(
                            state.value.cityList.find { it.name == newCity } ?: City()
                        ))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    EditableDropDown(
                        previousData = state.value.school.name,
                        label = stringResource(id = R.string.school),
                        options = state.value.schoolList.toListString(),
                        isError = state.value.schoolError,
                        errorText = ErrorString(id = R.string.null_textfield_error,addId = R.string.school)
                    ) { newSchool ->
                        viewModel.onEvent(RegistrationEducationEvent.OnSchoolChanged(
                            state.value.schoolList.find { it.name == newSchool } ?: School()
                        ))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ReadOnlyDropDown(
                        options = if(isWorkScreen) stringArrayResource(id = R.array.work_array).toList()
                            else stringArrayResource(id = R.array.grade).toList(),
                        previousData = state.value.grade,
                        label = if(isWorkScreen)stringResource(id = R.string.work_post) else stringResource(id = R.string.grade),
                        isError = state.value.gradeError,
                        errorText = ErrorString(
                            id = R.string.null_textfield_error,
                            addId = if(isWorkScreen)R.string.work_post else R.string.grade
                        )
                    ) {
                        viewModel.onEvent(RegistrationEducationEvent.OnGradeChanged(it))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FilledBtn(
                        text = stringResource(id = R.string.further),
                        padding = 0.dp
                    ) {
                        viewModel.onEvent(RegistrationEducationEvent.OnNext(navigator,isWorkScreen))
                    }
                }
            }
        }
    )
}

