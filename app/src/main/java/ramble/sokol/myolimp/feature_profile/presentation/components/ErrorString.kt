package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorString(id: Int,addId: Int): String {
    return stringResource(id = id, stringResource(id = addId).lowercase())
}