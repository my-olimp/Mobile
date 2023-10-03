package ramble.sokol.myolimp.feature_library.presenation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibrarySearchBar
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LoadingCircular
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType

@OptIn(ExperimentalMaterialApi::class)
@[Composable Destination]
fun SubjectsScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    subjectId: Long
) {
    val viewModel = getViewModel<SubjectViewModel>()
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onEvent(SubjectEvent.OnLoadSubjectContent(subjectId))
    }
    BottomBarTheme(
        navController = navController
    ) {
        val coroutineScope = rememberCoroutineScope()
        val modalSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            sheetContent = {
                if (state.value.isLoading) {
                    LoadingCircular()
                } else {

                }
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                LibrarySearchBar(
                    onSearchTextChanged = { newValue ->
                        viewModel.onEvent(SubjectEvent.OnSearchQueryUpdated(newValue))
                    },
                    onShowFavourites = { newValue ->
                        viewModel.onEvent(SubjectEvent.OnShowFavourites(newValue))
                    },
                    onShowFilterBottomSheet = { coroutineScope.launch { modalSheetState.show() } },
                    isFavoriteActive = state.value.isShowingFavourites
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = state.value.subjectName,
                        style = mediumType(
                            color = SheetTitle,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}