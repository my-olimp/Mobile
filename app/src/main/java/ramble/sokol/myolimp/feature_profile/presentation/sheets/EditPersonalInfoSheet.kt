package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ListDropDown
import ramble.sokol.myolimp.feature_profile.domain.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.presentation.components.CalendarInput
import ramble.sokol.myolimp.feature_profile.presentation.components.CheckBoxLabel
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditPersonalInfoSheet(
    viewModel: ProfileViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        OutlinedText(
            previousData = viewModel.state.value.secondName,
            label = stringResource(R.string.second_name),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSecondNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = viewModel.state.value.firstName,
            label = stringResource(R.string.name),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnFirstNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = viewModel.state.value.thirdName,
            label = stringResource(R.string.third_name),
            isEnabled = !viewModel.state.value.hasThird,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnThirdNameChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // https://stackoverflow.com/questions/72124384/how-do-i-set-border-color-for-the-checkbox-using-jetpack-compose
        CheckBoxLabel(
            label = stringResource(R.string.no_third_name),
            previousData = viewModel.state.value.hasThird,
            onStatusChanged = {
                viewModel.onEvent(ProfileEvent.OnMarkerClicked(it))
            }
        )

//        https://github.com/boguszpawlowski/ComposeCalendar
//        https://github.com/maxkeppeler/sheets-compose-dialogs

        Spacer(modifier = Modifier.height(14.dp))

        CalendarInput(
            label = stringResource(id = R.string.dob),
            previousData = viewModel.state.value.dateOfBirth
        ) {
            viewModel.onEvent(ProfileEvent.OnDobChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Spacer(modifier = Modifier.height(14.dp))

        ListDropDown(
            values = listOf(
                "Мужской", "Женский"
            ),
            label = stringResource(id = R.string.gender),
            previousData = viewModel.state.value.gender,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile_arrow_drop_down),
                    "contentDescription",
                    modifier = Modifier
                        .clip(CircleShape)
                )
            }
        ) {
            viewModel.onEvent(ProfileEvent.OnGenderChanged(it))
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = viewModel.state.value.snils,
            label = stringResource(R.string.snils),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnSnilsChanged(it))
            }
        )
        
        Spacer(modifier = Modifier.height(34.dp))

        FilledBtn(
            text = stringResource(id = R.string.save),
            padding = 0.dp
        ) {
            viewModel.onEvent(ProfileEvent.OnSave)
        }
    }
}
