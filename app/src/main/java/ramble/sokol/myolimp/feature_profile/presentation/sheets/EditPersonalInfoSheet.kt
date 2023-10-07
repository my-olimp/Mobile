package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_profile.presentation.components.CalendarInput
import ramble.sokol.myolimp.feature_profile.presentation.components.CheckBoxLabel
import ramble.sokol.myolimp.feature_profile.presentation.components.ErrorString
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditPersonalInfoSheet(
    viewModel: ProfileViewModel
) {

    val state = viewModel.personalState.collectAsState()
    val genders = stringArrayResource(id = R.array.genders).toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        OutlinedText(
            previousData = state.value.secondName ?: "",
            label = stringResource(R.string.second_name),
            isEnabled = true,
            isError = state.value.secondNameError,
            errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.surname_error),
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSecondNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.firstName ?: "",
            label = stringResource(R.string.name),
            isEnabled = true,
            isError = state.value.firstNameError,
            errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.name),
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnFirstNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.thirdName ?: "",
            label = stringResource(R.string.third_name),
            isEnabled = state.value.hasThird,
            isError = state.value.thirdNameError,
            errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.third_name),
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
            previousData = state.value.dateOfBirth ?: "",
            isError = state.value.dobError,
            errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.dob_error),
        ) {
            viewModel.onEvent(ProfileEvent.OnDobChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        ReadOnlyDropDown(
            options = genders,
            previousData = if (state.value.gender == "m") stringResource(id = R.string.man_gender) else stringResource(id = R.string.women_gender),
            label = stringResource(id = R.string.gender)
        ) {
            viewModel.onEvent(ProfileEvent.OnGenderChanged(
                if (genders[0] == it) "m" else "f"
            ))
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = state.value.snils ?: "",
            label = stringResource(R.string.snils),
            isError = state.value.snilsError,
            errorText = ErrorString(id = R.string.null_textfield_error, addId = R.string.snils),
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSnilsChanged(it))
            }
        )
        
        Spacer(modifier = Modifier.height(34.dp))

        FilledBtn(
            text = stringResource(id = R.string.save),
            padding = 0.dp
        ) {
            viewModel.onEvent(ProfileEvent.OnPersonalInfoSave)
        }
    }
}
