package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.ShowError
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.presentation.components.CalendarInput
import ramble.sokol.myolimp.feature_profile.presentation.components.CheckBoxLabel
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditPersonalInfoSheet(
    viewModel: ProfileViewModel
) {

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        OutlinedText(
            previousData = state.value.secondName ?: "Loading",
            label = stringResource(R.string.second_name),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSecondNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.firstName ?: "Loading",
            label = stringResource(R.string.name),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnFirstNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.thirdName ?: "Loading",
            label = stringResource(R.string.third_name),
            isEnabled = !state.value.hasThird,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnThirdNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // https://stackoverflow.com/questions/72124384/how-do-i-set-border-color-for-the-checkbox-using-jetpack-compose
        CheckBoxLabel(
            label = stringResource(R.string.no_third_name),
            previousData = state.value.hasThird,
            onStatusChanged = {
                viewModel.onEvent(ProfileEvent.OnMarkerClicked(it))
            }
        )

//        https://github.com/boguszpawlowski/ComposeCalendar
//        https://github.com/maxkeppeler/sheets-compose-dialogs

        Spacer(modifier = Modifier.height(14.dp))

        CalendarInput(
            label = stringResource(id = R.string.dob),
            previousData = state.value.dateOfBirth ?: "Loading"
        ) {
            viewModel.onEvent(ProfileEvent.OnDobChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        ReadOnlyDropDown(
           options =  listOf(
               "Мужской", "Женский"
           ),
            previousData = state.value.gender ?: "Loading",
            label = stringResource(id = R.string.gender)
        ) {
            viewModel.onEvent(ProfileEvent.OnGenderChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.snils ?: "Loading",
            label = stringResource(R.string.snils),
            isEnabled = state.value.snilsError,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSnilsChanged(it))
            }
        )
        if (state.value.snilsError) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                ShowError(text = stringResource(R.string.null_snils_error_text))
            }
        }
        
        Spacer(modifier = Modifier.height(34.dp))

        FilledBtn(
            text = stringResource(id = R.string.save),
            padding = 0.dp
        ) {
            viewModel.onEvent(ProfileEvent.OnPersonalInfoSave)
        }
    }
}
