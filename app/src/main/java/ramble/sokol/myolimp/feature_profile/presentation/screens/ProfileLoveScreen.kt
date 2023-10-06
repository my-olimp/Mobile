package ramble.sokol.myolimp.feature_profile.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ArticleScreenDestination
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LoadingCircular
import ramble.sokol.myolimp.feature_profile.domain.events.ProfileLoveEvent
import ramble.sokol.myolimp.feature_profile.presentation.components.CheckListBottomSheet
import ramble.sokol.myolimp.feature_profile.presentation.components.LoveTopBar
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileLoveViewModel
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.mediumType

@OptIn(ExperimentalMaterialApi::class)
@[Composable Destination]
fun ProfileLoveScreen(
    navController: NavController
) {

    val viewModel = getViewModel<ProfileLoveViewModel>()
    val state = viewModel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val subjects = state.value.filteredSubjects.ifEmpty { state.value.userSubjects }


    BottomBarTheme(
        navController = navController
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            sheetContent = {
                if (!state.value.isSubjectLoaded) {
                    LoadingCircular()
                } else {
                    CheckListBottomSheet(
                        subjects = state.value.subjects,
                        onHideSheet = { coroutineScope.launch { sheetState.hide() } },
                        onChooseItem = { key,value ->
                            viewModel.onEvent(ProfileLoveEvent.OnChooseCheckbox(key,value))
                        },
                        onFilter = { viewModel.onEvent(ProfileLoveEvent.OnFilterSubjects)}
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                LoveTopBar(
                    onTextChanged = { viewModel.onEvent(ProfileLoveEvent.OnQueryUpdate(it)) },
                    onFilterClick = { coroutineScope.launch { sheetState.show() } },
                    itemCount = state.value.filteredSubjects.size
                )

                VerticalSpacer(height = 12.dp)

                LibraryBox(
                    title = stringResource(id = R.string.library_articles_title),
                    onActionClicked = {}
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(state.value.listArticles.isNotEmpty()) {
                            items(state.value.listArticles) { article ->
                                if(subjects.contains(article.subject)) {
                                    LibraryItem(
                                        subject = article.subject,
                                        title = article.title,
                                        onClick = {
                                            navController.navigate(ArticleScreenDestination(id = article.id))
                                        }
                                    )
                                }
                            }

                        } else {
                            item {
                                TextReplacer(replacer = stringResource(id = R.string.have_no_loved_articles))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TextReplacer(replacer: String) {
    Text(
        text = replacer,
        style = mediumType(
            color = BlackProfile,
            fontSize = 14.sp
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}