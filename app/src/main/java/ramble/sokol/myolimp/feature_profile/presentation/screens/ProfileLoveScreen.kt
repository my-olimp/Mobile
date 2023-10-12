package ramble.sokol.myolimp.feature_profile.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
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
import ramble.sokol.myolimp.destinations.ProfileLoveScreenDestination
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.feature_profile.domain.events.ProfileLoveEvent
import ramble.sokol.myolimp.feature_profile.presentation.components.CheckListBottomSheet
import ramble.sokol.myolimp.feature_profile.presentation.components.LoveTopBar
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileLoveViewModel
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfileAchievement
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.regularType

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

    val articles = state.value.listArticles.filter { subjects.contains(it.subject) }

    BottomBarTheme(
        navigationBarColor = SecondaryScreen,
        statusBarColor = BackgroundMain,
        navController = navController,
        isLoading = !state.value.isSubjectLoaded || !state.value.isArticlesLoaded,
        onReload = {
            navController.navigate(ProfileLoveScreenDestination)
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            sheetContent = {
                CheckListBottomSheet(
                    subjects = state.value.subjects,
                    onHideSheet = { coroutineScope.launch { sheetState.hide() } },
                    onChooseItem = { key,value ->
                        viewModel.onEvent(ProfileLoveEvent.OnChooseCheckbox(key,value))
                    },
                    onFilter = { viewModel.onEvent(ProfileLoveEvent.OnFilterSubjects)}
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (!state.value.isSubjectLoaded || !state.value.isArticlesLoaded) 4.dp else 0.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LoveTopBar(
                    onTextChanged = {
                        viewModel.onEvent(ProfileLoveEvent.OnQueryUpdate(it))
                    },
                    onFilterClick = {
                        coroutineScope.launch { sheetState.show() }
                    },
                    onCancelSearching = {
                        viewModel.onEvent(ProfileLoveEvent.OnEmptyQuery)
                    },
                    itemCount = state.value.filteredSubjects.size,
                    previousData = state.value.queryText
                )

                VerticalSpacer(height = 12.dp)

                Column (
                    modifier = Modifier.padding(horizontal = 4.dp)
                ){

                    if(articles.isNotEmpty()) {
                        LibraryBox(
                            title = stringResource(id = R.string.library_articles_title),
                            onActionClicked = {}
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                items(articles) { article ->
                                    LibraryItem(
                                        subject = article.subject,
                                        title = article.title,
                                        onClick = {
                                            navController.navigate(ArticleScreenDestination(id = article.id))
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                       Image(
                           painter = painterResource(id = R.drawable.ic_no_loved_items),
                           contentDescription = "no loved items icon",
                           modifier = Modifier
                               .padding(start = 38.dp, end = 38.dp, top = 80.dp)
                               .fillMaxHeight(0.4F)
                               .fillMaxWidth()
                       ) 
                        
                       VerticalSpacer(height = 32.dp)
                       
                       Text(
                           text = stringResource(id = R.string.no_loved_yet),
                           style = regularType(
                               color = GreyProfileAchievement,
                               fontSize = 16.sp,
                               letterSpacing = 0.32.sp
                           ),
                           modifier = Modifier.fillMaxWidth(),
                           textAlign = TextAlign.Center
                       )
                    }
                }
            }
        }
    }
}
