package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileLoveViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLoveFilterSheet(
    viewModel: ProfileLoveViewModel,
    onDismiss: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.value.subjects) {
                CheckBoxLabel(label = it, onStatusChanged = {})
            }
        }
    }
}