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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.NewsScreenDestination
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
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

    val maybeInterestingList = mutableListOf<String>()

    for(i in 0..20) {
        newsList.add("item $i")
    }

    for (i in 0..4) {
        maybeInterestingList.add("maybe item $i")
    }

    val pages = newsList.size / 4

    var currentPage by remember {
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
                        .padding(horizontal = 4.dp, vertical = 12.dp),
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
                VerticalSpacer(height = 8.dp)
            }
            item {
                NewsSectionHeader(headerId = R.string.popular_header)
            }
            item {
                NewsItem(
                    title = "Скандал на Заключительном этапе ВСОш по информатике. Что произошло?",
                    date = "23 июня, 2023",
                    isSecondary = false,
                    isSaved = true
                ) {

                }
            }
            items(currentPage * 2) {
                val counter = it * 2
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NewsItem(
                        title = newsList[counter],
                        date = newsList[counter],
                        isSmall = true
                    ) {

                    }
                    NewsItem(
                        title = newsList[counter + 1],
                        date = newsList[counter] ,
                        isSecondary = false,
                        isSmall = true
                    ) {
                        
                    }
                }
            }
            if(currentPage < pages) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                currentPage += 1
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = stringResource(id = R.string.show_more),
                            style = mediumType(
                                color = BlackProfile,
                                fontSize = 14.sp
                            )
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_news_arrow_bottom),
                            contentDescription = "show more arrow",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            item {
                NewsSectionHeader(headerId = R.string.maybe_interesting)
            }
            items(maybeInterestingList.size / 2) {
                val counter = it * 2
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NewsItem(
                        title = maybeInterestingList[counter],
                        date = maybeInterestingList[counter],
                        isSmall = true
                    ) {

                    }
                    NewsItem(
                        title = maybeInterestingList[counter + 1],
                        date = maybeInterestingList[counter] ,
                        isSecondary = false,
                        isSmall = true
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun NewsSectionHeader(
    headerId: Int
) {
    Text(
        text = stringResource(id = headerId),
        style = mediumType(
            color = BlackProfile,
            fontSize = 20.sp,
            lineHeight = 42.sp
        ),
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
    )
}

/**
 *  [isSecondary] - костыль, который нужен чтобы в сетке итем не становился каждый
 *  в 2 раза меньше
 *
 *
 *  [isSmall] - параметр, отвечающий за то, будут ли у итема уменьшенные шрифты
 *  и отступы
 * **/

@Composable
private fun NewsItem(
    title: String,
    date: String,
    isSaved: Boolean = false,
    isSmall: Boolean = false,
    isSecondary: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(if (isSecondary) 0.5f else 1f)
            .fillMaxHeight(if (isSecondary) 0.5f else 1f)
            .padding(4.dp)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.TopStart

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.test_image_news),
                contentDescription = "test",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (isSmall) 8.dp else 16.dp,
                    end = if (isSmall) 10.dp else 28.dp,
                    top = if (isSmall) 8.dp else 16.dp
                )
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = if(isSmall) 10.sp else 15.sp,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontWeight = FontWeight(700),
                    color = White
                )
            )
            VerticalSpacer(height = 4.dp)
            Text(
                text = date,
                style = TextStyle(
                    fontSize = if(isSmall) 6.sp else 8.sp,
                    fontFamily = if(isSmall) FontFamily(Font(R.font.bold)) else FontFamily(Font(R.font.regular)),
                    fontWeight = if(isSmall) FontWeight(700) else FontWeight(400),
                    lineHeight = if(isSmall) 10.sp else 9.4.sp,
                    letterSpacing = if(isSmall) 0.72.sp else 0.96.sp,
                    color = White
                )
            )
        }
        Icon(
            painter = painterResource(
                id = if(isSaved) R.drawable.ic_news_filled_saved else R.drawable.ic_article_favorite
            ),
            contentDescription = "is saved news",
            tint = White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(if (isSmall) 8.dp else 16.dp)
                .size(if (isSmall) 16.dp else 32.dp)
        )
    }
}

@Composable
private fun NewsTag(
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
