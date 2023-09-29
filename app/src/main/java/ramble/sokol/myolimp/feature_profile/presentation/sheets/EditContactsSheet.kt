package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn

@Composable
fun EditContactsSheet (
    viewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedText(
            previousData = viewModel.state.value.email ?: "Loading",
            label = stringResource(R.string.email),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnEmailChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedText(
            previousData = viewModel.state.value.phone ?: "Loading",
            label = stringResource(R.string.phone_number),
            isEnabled = true,
            onTextChanged = {
                viewModel.onEvent(ProfileEvent.OnPhoneChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(34.dp))

        FilledBtn(
            text = stringResource(id = R.string.save),
            padding = 0.dp
        ) {
            viewModel.onEvent(ProfileEvent.OnSave("s"))
        }
    }
}
