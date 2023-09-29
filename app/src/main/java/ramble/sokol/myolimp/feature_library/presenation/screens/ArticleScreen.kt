package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.HorizontalLine
import ramble.sokol.myolimp.feature_library.presenation.components.PartItem
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.White


@[Composable Destination]
fun ArticleScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<ArticleViewModel>().also { it.fetchArticle() }
    
    val state = viewModel.state.collectAsState()

    var partState by remember {
        mutableIntStateOf(0)
    }

    OlimpTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                        tint = /*if (state.value.isShowingFavourites) White else*/ GreyProfileData,
                    )
                }

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
                        painter = painterResource(id = R.drawable.ic_calendar_favourite),
                        contentDescription = "bookmark",
                        tint = /*if (state.value.isShowingFavourites) White else*/ GreyProfileData,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalLine()

            /*                      after search box                    */

            //не уверен что тут нужен lazyrow а не просто row

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
                    /*TODO на будущее поменять*/else -> {
                    EducationScreen(
                        viewModel = viewModel,
                        blockId = partState
                    )
                }
                }
            }
        }
    }
}
