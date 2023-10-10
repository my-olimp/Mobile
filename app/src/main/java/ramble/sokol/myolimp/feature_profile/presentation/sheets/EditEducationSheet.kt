package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.feature_authentication.data.models.toListString
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.EditableDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.components.ErrorString
import ramble.sokol.myolimp.feature_profile.presentation.components.Loader
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditEducationSheet (
    viewModel: ProfileViewModel
) {
    val state = viewModel.educationState.collectAsState()

    var isLoaded by remember {
        mutableStateOf(false)
    }
    if(!isLoaded) {
        viewModel.onEvent(ProfileEvent.OnEducationSheetAttach(state.value.region))
        isLoaded = true
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
    ) {

        Column(
            modifier = Modifier.blur(if (state.value.isLoading) 4.dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EditableDropDown(
                options = state.value.regionList.toListString(),
                previousData = state.value.region.name,
                label = stringResource(R.string.region_profile),
                isError = state.value.regionError,
                errorText = ErrorString(
                    id = R.string.null_textfield_error,
                    addId = R.string.region_profile
                )
            ) { newRegion ->
                viewModel.onEvent(ProfileEvent.OnRegionChanged(
                    state.value.regionList.find { it.name == newRegion } ?: Region()
                ))
            }

            Spacer(modifier = Modifier.height(14.dp))

            EditableDropDown(
                options = state.value.cityList.toListString(),
                previousData = state.value.city.name,
                label = stringResource(R.string.city_profile),
                isError = state.value.cityError,
                errorText = ErrorString(
                    id = R.string.null_textfield_error,
                    addId = R.string.city_profile
                )
            ) { newCity ->
                viewModel.onEvent(ProfileEvent.OnCityChanged(
                    state.value.cityList.find { it.name == newCity } ?: City()
                ))
            }

            Spacer(modifier = Modifier.height(14.dp))

            EditableDropDown(
                options = state.value.schoolList.toListString(),
                previousData = state.value.school.name,
                label = stringResource(R.string.school),
                isError = state.value.schoolError,
                errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.school)
            ) { newSchool ->
                viewModel.onEvent(ProfileEvent.OnSchoolChanged(
                    state.value.schoolList.find { it.name == newSchool } ?: School()
                ))
            }

            Spacer(modifier = Modifier.height(14.dp))

            ReadOnlyDropDown(
                previousData = "${state.value.grade}",
                label = stringResource(R.string.grade),
                options = stringArrayResource(id = R.array.grade).toList(),
                isError = state.value.gradeError,
                errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.grade)
            ) {
                try {
                    viewModel.onEvent(ProfileEvent.OnGradeChanged(it.toInt()))
                } catch (_: Exception) {
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            FilledBtn(
                text = stringResource(id = R.string.save),
                padding = 0.dp
            ) {
                viewModel.onEvent(ProfileEvent.OnEducationInfoSave)
            }
        }
        Box(
            modifier = Modifier.height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            if(state.value.isLoading) Loader(modifier = Modifier.fillMaxSize())
        }
    }
}
