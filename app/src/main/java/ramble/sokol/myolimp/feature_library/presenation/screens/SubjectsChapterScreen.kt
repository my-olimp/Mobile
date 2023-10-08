package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import ramble.sokol.myolimp.feature_library.domain.events.ChapterEvent
import ramble.sokol.myolimp.feature_library.domain.view_models.SubjectsChapterViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.SheetTitle

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

    OlimpTheme(
        navigationBarColor = SecondaryScreen,
        isLoading = state.value.isLoading
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
            Text(
                text = subject,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontWeight = FontWeight(500),
                    color = SheetTitle,
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LibraryBox(
                title = "Первый шаг",
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
        }
    }
}
