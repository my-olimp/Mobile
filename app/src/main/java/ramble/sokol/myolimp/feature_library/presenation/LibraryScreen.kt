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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BottomBarTheme

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
        /*
         search & bookmarks
        */
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
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
                    onClick = {
                        viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                    },
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
            LibraryBox(title = stringResource(R.string.library_articles_title)) {
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
                            type = article.type,
                            subject = article.subject,
                            title = article.title,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun LoadingCircular() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}