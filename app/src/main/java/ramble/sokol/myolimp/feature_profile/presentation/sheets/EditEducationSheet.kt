package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.data.models.toListString
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.EditableDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditEducationSheet (
    viewModel: ProfileViewModel
) {
    val state = viewModel.state.collectAsState()
    val isUpdated = remember {
        mutableStateOf(false)
    }
    if(!isUpdated.value){
        viewModel.updateRegionsList()
        isUpdated.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditableDropDown(
            options = state.value.regionList.toListString().ifEmpty {
                stringArrayResource(id = R.array.region).toList()
            },
            previousData = state.value.region.name,
            label = stringResource(R.string.region_profile),
        ) { newRegion ->
            state.value.regionList.find {
                it.name == newRegion
            }?.let {
                viewModel.onEvent(ProfileEvent.OnRegionChanged(it))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        EditableDropDown(
            options = state.value.cityList.toListString().ifEmpty {
                stringArrayResource(id = R.array.city).toList()
            },
            previousData = state.value.city.name,
            label = stringResource(R.string.city_profile)
        ) { newCity ->
            state.value.cityList.find {
                it.name == newCity
            }?.let {
                viewModel.onEvent(ProfileEvent.OnCityChanged(it))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        EditableDropDown(
            options = state.value.schoolList.toListString().ifEmpty {
                stringArrayResource(id = R.array.school).toList()
            },
            previousData = state.value.school.name,
            label = stringResource(R.string.school)
        ) { newSchool ->
            state.value.schoolList.find {
                it.name == newSchool
            }?.let {
                viewModel.onEvent(ProfileEvent.OnSchoolChanged(it))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        ReadOnlyDropDown(
            previousData = "${state.value.grade}",
            label = stringResource(R.string.grade),
            options = listOf(
                "1", "2",
                "3", "4",
                "5", "6",
                "7", "8",
                "9", "10",
                "11",
            )
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
            viewModel.onEvent(ProfileEvent.OnSave)
        }
    }
}
