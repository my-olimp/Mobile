package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ArticleScreenDestination
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.article.HorizontalLine
import ramble.sokol.myolimp.feature_library.presenation.components.article.PartItem
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.FavoriteIcon
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White


@[Composable Destination]
fun ArticleScreen(
    navController: NavController,
    id: Int
) {
    val viewModel = getViewModel<ArticleViewModel>()
    
    val state = viewModel.state.collectAsState()

    var partState by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(ArticleEvent.OnFetchArticle(id))
    })

    OlimpTheme(
        isLoading = state.value.isLoading,
        onReload = {
            navController.navigate(ArticleScreenDestination(id))
        },
        statusBarColor = BackgroundMain,
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(if (state.value.isLoading) 4.dp else 0.dp)
                    .background(color = BackgroundMain)
            ) {

                Row(
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Max)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SearchTextField(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .weight(0.68f),
                        onTextChanged = {
                            //viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(it))
                        },
                        onCancelSearching = {
                            //viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(""))
                        }
                    )

                    Spacer(modifier = Modifier.fillMaxWidth(0.02f))

                    Box(
                        modifier = Modifier
                            //.fillMaxHeight()
                            .weight(0.16f)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(top = 8.dp)
                            .background(
                                color = White,
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .clickable {
                                //viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                            },
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            painter = painterResource(id = R.drawable.ic_library_share),
                            contentDescription = "bookmark",
                            tint = /* if (state.value.article.isFavourite) White else */  GreyProfileData,
                        )
                    }

                    Spacer(modifier = Modifier.fillMaxWidth(0.02f))

                    FavoriteIcon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.15f)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(8.dp)), //try remove
                        onClick = {
                            viewModel.onEvent(ArticleEvent.OnFavourites(!state.value.article.isFavourite))
                        },
                        isActive = state.value.article.isFavourite
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalLine()

                /*
                    after search box
                */

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(state.value.article.blocks.size) {
                        PartItem(
                            itemId = it,
                            selected = partState == it,
                            partType = state.value.article.blocks[it].type
                        ) { id ->
                            partState = id
                        }
                    }
                }

                HorizontalLine()

                /*              select parts               */
                if(state.value.article.blocks.isNotEmpty()) {
                    when (state.value.article.blocks[partState].type) {
                        "p" -> {
                            ExaminationScreen(
                                viewModel = viewModel,
                                blockId = partState
                            )
                        }
                        /*TODO на будущее поменять*/
                        else -> {
                            EducationScreen(
                                viewModel = viewModel,
                                blockId = partState
                            )
                        }
                    }
                }

            }
        }
    )
}