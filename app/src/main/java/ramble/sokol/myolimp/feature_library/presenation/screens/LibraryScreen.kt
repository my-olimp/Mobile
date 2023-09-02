package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_library.domain.events.LibraryEvent
import ramble.sokol.myolimp.feature_library.domain.view_models.LibraryViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.SearchTextField
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White

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
                }
            )

            Spacer(modifier = Modifier.fillMaxWidth(0.01f))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.15f)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (state.value.isShowingFavourites) BlueStart else White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .clickable {
                        viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                    },
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_library_sorting),
                    contentDescription = "bookmark",
                    tint = if (state.value.isShowingFavourites) White else GreyProfileData,
                )
            }

            Spacer(modifier = Modifier.fillMaxWidth(0.01f))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.15f)
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (state.value.isShowingFavourites) BlueStart else White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .clickable {
                        viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                    },
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_calendar_favourite),
                    contentDescription = "bookmark",
                    tint = if (state.value.isShowingFavourites) White else GreyProfileData,
                )
            }
        }
    }
}