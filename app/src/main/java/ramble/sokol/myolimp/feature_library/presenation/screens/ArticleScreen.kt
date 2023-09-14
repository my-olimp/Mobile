package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.components.SearchTextField
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.LibraryItemSelected
import ramble.sokol.myolimp.ui.theme.LibraryLine
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

@[Preview(showBackground = true) Composable]
fun PreviewArticleScreen() {
    ArticleScreen()
}

@Composable
fun ArticleScreen(

) {

    val testItems = listOf(
        R.drawable.ic_library_read_item,
        R.drawable.ic_library_read_item,
        R.drawable.ic_library_read_item,
        R.drawable.ic_library_read_item,
        R.drawable.ic_library_read_item,
        R.drawable.ic_library_read_item
    )

    OlimpTheme {
        Column (
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
                    .padding(vertical = 5.dp, horizontal = 16.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(15) {
                    PreviewItem()
                }
            }
            /*              after lazyrow               */

            Image(
                painter = painterResource(id = R.drawable.library_test_image),
                contentDescription = "test image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(120.dp) /*TODO wtf*/
            )

            Spacer(modifier = Modifier.height(12.dp))

            /*            after image                    */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = "Уравнение второго порядка",
                    style = mediumType(
                        fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Автор: Иван Иванов",
                    style = regularType(
                        fontSize = 12.sp,
                        letterSpacing = 0.24.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    items(10) {
                        SubjectItem(subjectText = "math meth $it")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))


                /*INTRODUCTION*/
                Text(
                    text = "Методы решения уравнений третьей и четвёртой степени (формула Кардано и метод Феррари) выходят за рамки программы обычной школы. Поэтому если на олимпиаде вам попадается уравнение степени 3 или выше, то следует искать искусственный приём, приспособленный для решения именно этого уравнения. Таким приёмом может быть, например, удачная группировка с последующим разложением на множители или выявление устойчивых выражений с соответствующей заменой переменной. Данная статья посвящена уравнениям вида p(x) = 0, где p(x) — многочлен третьей степени и выше, и некоторым приёмам разложения такого многочлена на множители.",
                    style = regularType(
                        color = SheetTitle,
                        letterSpacing = 0.22.sp,
                        fontSize = 11.sp,
                        lineHeight = 15.62.sp
                    )
                )

                /*HEADERS*/
                Text(
                    text = "1. Непосредственная группировка",
                    style = mediumType(
                        letterSpacing = 0.22.sp,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(horizontal = 2.dp,vertical = 5.dp)
                )
                /*CONTENT*/
                Text(
                    text = "В простейших случаях многочлен удаётся разложить на множители, удачно группируя друг с другом слагаемые.",
                    style = regularType(
                        color = SheetTitle,
                        letterSpacing = 0.22.sp,
                        fontSize = 11.sp,
                        lineHeight = 15.62.sp
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))
                /*TASKS*/
                TextTask()
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

@Composable
fun TextTask() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        VerticalLine()

        Spacer(modifier = Modifier.width(5.dp))

        Column (
            modifier = Modifier.padding(vertical = 5.dp)
        ){
            Text(
                text = "Задача 1. Решить уравнение 2x3 − 3x2 − 8x + 12 = 0.",
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp
                )
            )
            /*SOLUTION*/
            Text(
                text = "Решение: ",
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp
                ),
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Text(
                text = "x2(2x − 3) − 4(2x − 3) = 0 \n" +
                        "(2x − 3)(x2 − 4) = 0 \n" +
                        "(2x − 3)(x − 2)(x + 2) = 0.",
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp,
                    lineHeight = 15.62.sp
                )
            )
            /*ANSWER*/
            Text(
                text = "Ответ: 3/2, ±2.",
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp
                ),
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}


@Composable
fun PreviewItem(
) {
    Column(
        modifier = Modifier
            .background(
                color = LibraryItemSelected,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(all = 5.dp)
    ) {
      Icon(
          painter = painterResource(id = R.drawable.ic_library_read_item),
          contentDescription = "read item",
          modifier = Modifier
              .padding(all = 6.dp),
          tint = White
          )
    }
}

@Composable
fun HorizontalLine(
    height: Dp = 1.dp
) {
    Spacer(
        modifier = Modifier
            .background(color = LibraryLine)
            .height(height)
            .fillMaxWidth()
    )
}

@Composable
fun VerticalLine(
    width: Dp = 2.dp
) {
    Spacer(
        modifier = Modifier
            .background(BlueStart)
            .width(width)
            .fillMaxHeight()
    )
}