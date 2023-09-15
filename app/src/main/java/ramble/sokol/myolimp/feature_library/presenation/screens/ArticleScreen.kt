package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.components.HorizontalLine
import ramble.sokol.myolimp.feature_library.presenation.components.PartItem
import ramble.sokol.myolimp.feature_library.presenation.components.SearchTextField
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType

/*@[Preview(showBackground = true) Composable]
fun PreviewArticleScreen() {
    ArticleScreen()
}*/

@[Composable Destination]
fun ArticleScreen(
    navigator: DestinationsNavigator
) {

    var partState by remember {
        mutableIntStateOf(0)
    }

    OlimpTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundMain)
                //.verticalScroll(rememberScrollState())
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
                        //viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(it))
                    },
                    onCancelSearching = {
                        //viewModel.onEvent(LibraryEvent.OnSearchQueryUpdated(""))
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
                            .padding(12.dp),
                        painter = painterResource(id = R.drawable.ic_library_sorting),
                        contentDescription = "bookmark",
                        //tint = if (state.value.isShowingFavourites) White else GreyProfileData,
                    )
                }

                Spacer(modifier = Modifier.fillMaxWidth(0.01f))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.15f)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        /*.background(
                        //color = if (state.value.isShowingFavourites) BlueStart else White,
                        //shape = RoundedCornerShape(size = 8.dp)
                    )*/
                        .clickable {
                            //viewModel.onEvent(LibraryEvent.OnShowFavourites(!state.value.isShowingFavourites))
                        },
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        painter = painterResource(id = R.drawable.ic_calendar_favourite),
                        contentDescription = "bookmark",
                        //tint = if (state.value.isShowingFavourites) White else GreyProfileData,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalLine()

            /*                      after search box                    */
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                for(i in 0..15) {
                    item {
                        PartItem(
                            onClick = { id ->
                                partState = id
                            },
                            itemId = i,
                            selected = partState == i
                        )
                    }
                }
            }
            /*              select parts               */
            when(partState) {
                0 -> ExaminationScreen()
                else -> EducationScreen()
            }
        }
    }
}

@Composable
fun SubjectItem(subjectText: String) {
    Column(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(5.dp),
                color = BlueStart
            )
            .padding(horizontal = 3.dp, vertical = 2.dp)
    ) {
        Text(
            text = subjectText,
            color = BlueStart,
            style = regularType(
                fontSize = 10.sp,
                letterSpacing = 0.2.sp
            )
        )
    }
}
