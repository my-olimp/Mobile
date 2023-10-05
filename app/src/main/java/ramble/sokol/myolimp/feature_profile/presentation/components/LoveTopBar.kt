package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.SubjectsPickerBottomSheet
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LoadingCircular
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileLoveViewModel
import ramble.sokol.myolimp.ui.theme.White


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoveTopBar(
    viewModel: ProfileLoveViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val state = viewModel.state.collectAsState()


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        sheetContent = {
            if (!state.value.isLoaded) {
                LoadingCircular()
            } else {
                CheckListBottomSheet(
                    subjectsMap = state.value.subjects,
                    onEvent = viewModel::onEvent,
                    onHideSheet = { coroutineScope.launch { sheetState.hide() } }
                )
            }
        }
    ) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                modifier = Modifier.weight(0.83f),
                onTextChanged = {/*TODO*/ }
            ) {

            }
            Spacer(modifier = Modifier.weight(0.04f))


            Box(
                modifier = Modifier
                    .weight(0.13f)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 8.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .clickable {
                        coroutineScope.launch { sheetState.show() }
                    }
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(vertical = 4.dp, horizontal = 4.dp),
                    painter = painterResource(id = R.drawable.ic_setting_love),
                    contentDescription = "open filter love"
                )

            }
        }
    }
}