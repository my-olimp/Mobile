package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditableDropDown (
            options = listOf(
                "Московская область", "Краснодарская область",
                "Нижегородская область", "Ленинградская область"
            ),
            previousData = state.value.region.name,
            label = stringResource(R.string.region_profile),
        ) {
            // TODO
//            viewModel.onEvent(ProfileEvent.OnRegionChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        EditableDropDown (
            previousData = state.value.city.name,
            label = stringResource(R.string.city_profile),
            listOf(
                "Балашиха", "Москва",
                "Санкт-Петербург", "Чехов"
            )
        ) {
            //TODO
            //viewModel.onEvent(ProfileEvent.OnCityChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        EditableDropDown (
            previousData = state.value.school.name,
            label = stringResource(R.string.school),
            listOf(
                "МБОУ СОШ №10", "МБОУ СОШ №1",
                "МБОУ гимназия №2", "МБОУ СОШ №3",
                "МБОУ лицей №4",
            )
        ) {//TODO
            //viewModel.onEvent(ProfileEvent.OnSchoolChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        ReadOnlyDropDown (
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
            } catch (_: Exception) {}
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
