package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ArticleScreenDestination
import ramble.sokol.myolimp.destinations.LibraryScreenDestination
import ramble.sokol.myolimp.destinations.SubjectsChapterScreenDestination
import ramble.sokol.myolimp.feature_library.domain.events.ChapterEvent
import ramble.sokol.myolimp.feature_library.domain.view_models.SubjectsChapterViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.article.Discipline
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.FavoriteIcon
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.SheetTitle

@OptIn(ExperimentalLayoutApi::class)
@Destination
@Composable
fun SubjectsChapterScreen (
    navController: NavController,
    discipline: String
) {

    val viewModel = getViewModel<SubjectsChapterViewModel>()
    val state = viewModel.state.collectAsState()

    val subject by remember {
        mutableStateOf(
            discipline
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(ChapterEvent.OnSearchArticlesBySubject(subject = subject))
    })

    BottomBarTheme(
        navigationBarColor = SecondaryScreen,
        statusBarColor = BackgroundMain,
        isLoading = state.value.isLoading,
        navController = navController,
        onReload = {
            navController.navigate(SubjectsChapterScreenDestination(discipline = subject))
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    if (state.value.isLoading) 4.dp else 0.dp
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
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
                    onTextChanged = {
                        viewModel.onEvent(ChapterEvent.OnSearchQueryUpdated(it))
                    }
                ) {
                    viewModel.onEvent(ChapterEvent.OnEmptyQuery)

                }

                Spacer(modifier = Modifier.weight(0.02f))

                FavoriteIcon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.15f)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    onClick = {
                        viewModel.onEvent(ChapterEvent.OnShowFavourites(!state.value.isShowingFavourites))
                    },
                    isActive = state.value.isShowingFavourites
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = subject,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = SheetTitle,
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LibraryBox(
                title = "Статьи",
                action = "",
                onActionClicked = {}
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(state.value.articles) { article ->
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
            
            Spacer(modifier = Modifier.height(16.dp))

            LibraryBox(
                title = "Разделы",
                action = "",
                onActionClicked = {}
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 4
                ) {
//                    state.value.articles.forEach { article ->
//                        article.tags.forEach { tag->
//                            Discipline(
//                                text = tag,
//                                onClick = {}
//                            )
//                        }
//                    }

                    for (i in 1..10) {
                        Discipline(
                            text = "test - $i",
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}
