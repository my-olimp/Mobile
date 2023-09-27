package ramble.sokol.myolimp.feature_library.presenation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.LibraryItem
import ramble.sokol.myolimp.feature_library.presenation.LibrarySearchBar
import ramble.sokol.myolimp.feature_library.presenation.SubjectsPickerBottomSheet
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.MainPageBlue

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun LibraryScreen(
    navController: NavController,
) {
    val viewModel = getViewModel<LibraryViewModel>()
    val state = viewModel.state.collectAsState()
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
                    SubjectsPickerBottomSheet(
                        subjectsMap = state.value.bottomSheetSubjectsMap,
                        onEvent = viewModel::onEvent,
                        onHideSheet = { coroutineScope.launch { modalSheetState.hide() } }
                    )
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
                        viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(newValue))
                    },
                    onShowFavourites = { newValue ->
                        viewModel.onEvent(LibraryEvent.OnShowFavourites(newValue))
                    },
                    onShowFilterBottomSheet = { coroutineScope.launch { modalSheetState.show() } },
                    isFilterActive = state.value.filteredSubjects != emptyList<String>(),
                    isFavoriteActive = state.value.isShowingFavourites
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        LibraryBox(
                            title = stringResource(R.string.library_articles_title),
                            isLearnMore = false
                        ) {
                            if (state.value.isLoading) {
                                LoadingCircular()
                            }
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                items(state.value.articles) { article ->
                                    LibraryItem(
                                        //type = article.blocks,
                                        subject = article.subject,
                                        title = article.title,
                                    )
                                }
                            }
                        }
                    }
                    val userSubjects = if (state.value.filteredSubjects == emptyList<String>()) {
                        state.value.userSubjects
                    } else {
                        state.value.filteredSubjects
                    }
                    items(userSubjects) { subject ->
                        LibraryBox(title = subject) {
                            val subjectArticles =
                                state.value.articles.filter { it.subject == subject }
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                items(subjectArticles) { article ->
                                    LibraryItem(
                                        subject = article.subject,
                                        title = article.title,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun LoadingCircular(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MainPageBlue
        )
    }
}