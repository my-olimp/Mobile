package ramble.sokol.myolimp.feature_library.presenation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.CalendarFocusedText
import ramble.sokol.myolimp.ui.theme.CalendarUnFocusedText
import ramble.sokol.myolimp.ui.theme.CloseIconColor
import ramble.sokol.myolimp.ui.theme.ErrorAccent
import ramble.sokol.myolimp.ui.theme.MainPageBlue
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun LibraryScreen(
    navController: NavController
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
            sheetContent = {
                if (state.value.isLoading) {
                    LoadingCircular()
                } else {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.subject),
                                style = regularType(
                                    color = CalendarUnFocusedText,
                                    fontSize = 18.sp
                                ),
                            )
                            IconButton(
                                onClick = {
                                    coroutineScope.launch { modalSheetState.hide() }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close bottom sheet",
                                    tint = CloseIconColor
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            val subjectsMap = state.value.bottomSheetSubjectsMap
                            subjectsMap.keys.forEach { subjectTitle ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Checkbox(
                                        checked = subjectsMap[subjectTitle] ?: false,
                                        onCheckedChange = {
                                            viewModel.onEvent(
                                                LibraryEvent.OnCheckboxSubject(subjectTitle)
                                            )
                                        },
                                        modifier = Modifier.size(24.dp),
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = CalendarFocusedText,
                                            uncheckedColor = White,
                                            checkmarkColor = ErrorAccent, //TODO change
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Text(
                                        text = subjectTitle,
                                        style = regularType(
                                            color = SheetTitle,
                                            fontSize = 18.sp
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(48.dp))
                        FilledBtn(
                            text = stringResource(R.string.button_show_text),
                            isEnabled = state.value.bottomSheetSubjectsMap.any { it.value } //At least one true
                        ) {
                            coroutineScope.launch { modalSheetState.hide() }
                            viewModel.onEvent(LibraryEvent.OnFilterSubjectFromBottomSheet)
                        }
                    }
                }
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Max)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    SearchTextField(
                        modifier = Modifier
                            .weight(0.68f),
                        onTextChanged = {
                            viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(it))
                        },
                        onCancelSearching = {
                            viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(""))
                        }
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.01f))
                    FilterIcon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.15f)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(8.dp)), //try remove
                        onClick = { coroutineScope.launch { modalSheetState.show() } },
                        isActive = state.value.isShowingFavourites
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.01f))
                    FavoriteIcon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.15f)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(8.dp)), //try remove
                        onClick = {
                            viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                        },
                        isActive = state.value.isShowingFavourites
                    )
                }
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
                    items(state.value.userSubjects) { subject ->
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