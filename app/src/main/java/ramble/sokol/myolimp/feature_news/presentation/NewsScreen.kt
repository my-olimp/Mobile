package ramble.sokol.myolimp.feature_news.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.NewsScreenDestination
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_profile.presentation.components.HorizontalSpacer
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

@Destination
@Composable
fun NewsScreen(
    navController: NavController
) {
    val testList = listOf("aaa","bbb","ccc","ddd","heiße","spreche")
    val boolList = mutableListOf(false,false,false,false,false,false)

    val newsList = mutableListOf<String>()

    for(i in 0..20) {
        newsList.add("item $i")
    }

    val pages = newsList.size % 4

    val currentPage by remember {
        mutableIntStateOf(1)
    }

    BottomBarTheme(
        navController = navController,
        onReload = {
            navController.navigate(NewsScreenDestination)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 60.dp)
                .fillMaxSize()
        ) {
            item {
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp, vertical = 12.dp),
                    onTextChanged = {},
                    onCancelSearching = {},
                    previousData = ""
                )
            }
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_news_tags_search),
                            contentDescription = "first elem of lazyrow"
                        )
                    }
                    items(testList.size) {
                        NewsTag(
                            title = testList[it],
                            onClick = { value ->
                                boolList[it] = value
                            }
                        )
                    }
                }
                VerticalSpacer(height = 12.dp)
            }
            item {
                NewsSectionHeader(headerId = R.string.popular_header)
            }
            item {
                NewsItem(title = "Скандал на Заключительном этапе ВСОш по информатике. Что произошло?",date = "23 июня, 2023", isSecondary = false) {

                }
            }
            items(currentPage * 2) {
                val counter = it * 2
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NewsItem(title = newsList[counter], date = newsList[counter]) {

                    }
                    //HorizontalSpacer(width = 8.dp)
                    NewsItem(title = newsList[counter + 1],date = newsList[counter] ,isSecondary = false) {
                        
                    }
                }
            }
        }
    }
}

@Composable
fun NewsSectionHeader(
    headerId: Int
) {
    Text(
        text = stringResource(id = headerId),
        style = mediumType(
            color = BlackProfile,
            fontSize = 20.sp,
            lineHeight = 42.sp
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}
@Composable
fun NewsItem(
    title: String,
    date: String,
    isSaved: Boolean = false,
    isSecondary: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(if (isSecondary) 0.5f else 1f)
            .fillMaxHeight(if (isSecondary) 0.5f else 1f)
            .padding(4.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.test_image_news),
                contentDescription = "test",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = title,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.bold)),
                fontWeight = FontWeight(700),
                color = White
            ),
            modifier = Modifier.padding(
                start = if(isSecondary) 8.dp else 16.dp,
                end = if(isSecondary) 10.dp else 28.dp,
                top = if(isSecondary) 8.dp else 16.dp
            )
        )

       Text(
            text = date
        )
    }
}

@Composable
fun NewsTag(
    title: String,
    onClick: (Boolean) -> Unit
) {

    var isChosen by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(18.dp),
                color = if (isChosen) BlueStart else BlackProfile
            )
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                isChosen = !isChosen
                onClick.invoke(isChosen)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = regularType(
                color = if(isChosen) BlueStart else BlackProfile,
                fontSize = 10.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}
