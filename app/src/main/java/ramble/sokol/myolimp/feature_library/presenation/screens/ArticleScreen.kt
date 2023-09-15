package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.data.models.SubjectModel
import ramble.sokol.myolimp.feature_authentication.presentation.components.SubjectComponent
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

@OptIn(ExperimentalLayoutApi::class)
@[Composable /*Destination*/]
fun ArticleScreen(
    //navigator: DestinationsNavigator
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
                .verticalScroll(rememberScrollState())
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

                LazyRow (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    items(10) {
                        SubjectItem(subjectText = "math meth $it")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                /*FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for(i in 0..10) {
                        //SubjectItem(subjectText = "math meth $i")
                        SubjectComponent(
                            subject = SubjectModel(name = "meth $i"),
                            previouslySelected = false,
                            onClick = {}
                        )
                    }
                }*/

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
                TextTask(
                    TaskText = "Задача 1. Решить уравнение 2x3 − 3x2 − 8x + 12 = 0.",
                    SolutionText = "x2(2x − 3) − 4(2x − 3) = 0 \n" +
                            "(2x − 3)(x2 − 4) = 0 \n" +
                            "(2x − 3)(x − 2)(x + 2) = 0.",
                    AnswerText = "Ответ: 3/2, ±2."
                )

                Spacer(modifier = Modifier.height(6.dp))

                TextTask(
                    TaskText = "Задача 2. (МГУ, социологич. ф-т, 2004 ) Решите уравнение:\n" +
                            "x3 + 9x2 + 18x − 2(x 2 + 9x) − 36\n" +
                            "                     √ x + 3   ",
                    SolutionText = " Если поддаться искушению раскрыть скобки и привести подобные слагаемые (тем более что сократится 18x), то в возникшем кубическом уравнении придётся подбирать корень с целью разложить левую часть на множители. Данная процедура описана в следующем пункте и не представляет здесь никаких сложностей, однако необходимости в ней сейчас нет. Дело в том, что несколько вычурная запись условия содержит подсказку, как именно надо группировать слагаемые. Имеем:  \n" +
                            "x3 + 9x2 + 18x − 2(x2 + 9x) − 36 = x(x2 + 9x) − 2(x2 + 9x) + 18x − 36 = (x 2 + 9x)(x − 2) + 18(x − 2) = (x − 2)(x2 + 9x + 18) = (x − 2)(x + 3)(x + 6).\n" +
                            "Наше уравнение, таким образом, равносильно системе\n" +
                            "   (x − 2)(x + 3)(x + 6) = 0, \n" +
                            "    x + 3 > 0, \n" +
                            "решением которой служит x = 2.",
                    AnswerText = "Ответ: 2."
                )
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
fun TextTask(
    TaskText: String = "",
    SolutionText: String = "",
    AnswerText: String = ""
) {
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
                text = TaskText,
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
                text = SolutionText,
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp,
                    lineHeight = 15.62.sp
                )
            )
            /*ANSWER*/
            Text(
                text = AnswerText,
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