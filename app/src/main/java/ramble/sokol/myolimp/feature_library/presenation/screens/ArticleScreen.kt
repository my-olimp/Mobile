package ramble.sokol.myolimp.feature_library.presenation.screens

import android.webkit.WebView
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.article.HorizontalLine
import ramble.sokol.myolimp.feature_library.presenation.components.article.PartItem
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.FavoriteIcon
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LoadingCircular
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.White


@[Composable Destination]
fun ArticleScreen(
    id: Int
) {
    val viewModel = getViewModel<ArticleViewModel>().also {
        it.fetchArticle(id = id)
    }
    
    val state = viewModel.state.collectAsState()

    var partState by remember {
        mutableIntStateOf(0)
    }

//    Latex()

    //MarkDown()

    OlimpTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundMain)
        ) {

            if (state.value.isLoading) {
                LoadingCircular()
            }

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

@Composable
fun MarkDown() {

    val text = """
        Фонетика и фонология

        **Фонетика** – звуковой состав языка, а также раздел лингвистики, изучающий звуковую сторону языка. Фонетика изучает порождение и восприятие звуков, их акустические и артикуляционные свойства, слоги, фонетические слова и интонацию.

        **Фонология** – раздел лингвистики, изучающий функционирование звуков в языковой системе и в сознании носителя.

        Разница между этими терминами, грубо говоря, в том, что фонология изучает то, что находится в голове у носителя, как в его сознании хранится представление о *фонемах*, а фонетика изучает то, как эти фонемы реализуются в *звуки*, которые мы слышим.

        **Основными единицами фонетики являются:**

        - **звук** – каждая конкретная реализация фонемы; звуков бесконечное количество
        - **звукотип** – группа звуков, воспринимаемая носителями одинаково (в школе именно звукотип называют звуком)
        - **слог** – фонетическое единство, произнесённое на одном толчке воздуха, составленное вокруг слогообразующего элемента (проще говоря, это почти всегда один гласный с примыкающими к нему согласными)
        - **фонетическое** **слово** – фонетическое единство, образованное вокруг одного ударного элемента
        - **синтагма** – интонационное фонетическое единство (т. е. несколько слов, объединённых интонацией)
        - **фраза** – фонетическое единство, отделённое с обеих сторон паузами

        Задачи по фонетике встречаются на всех олимпиадах по русскому языку. В них обычно рассматриваются артикуляционные свойства конкретных звуков (посмотреть артикуляционные профили можно здесь: [https://seeingspeech.ac.uk](https://seeingspeech.ac.uk/)) исторические изменения в произношении слов, территориальные или индивидуальные особенности произношения, а также языковая игра, основанная на схожести звучания и т.д.

        **Примеры задач по фонетике:** https://docs.google.com/document/d/137wqYWOYslc8-ZuVNzfvw2A5ZTMLwi22kyf_tDIiWAI/edit

        **Список литературы по фонетике и фонологии:**

        Князев С. В., Пожарицкая С. К. «Современный русский язык. Фонетика: учебник для бакалавриата и магистратуры»

        Трубецкой Н. С. «Основы фонологии»

        «Русская грамматика» / Н. Ю. Шведова (гл. ред.) Т. 1: Фонетика. Фонология. Ударение. Интонация. Словообразование. Морфология

        Чуковский К. И. «От двух до пяти»

         Аванта+. Энциклопедия для детей. Том 10. «Языкознание. Русский язык»

        Кодзасов С. В., Кривнова О. Ф. «Общая фонетика»
    """.trimIndent()

    MarkdownText(markdown = text)
}

@Composable
private fun Latex() {

    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
            }
        },
        update = {webView ->
            webView.loadData(
                formatLaTeXHtml(""),
                "text/html", "utf-8"
            )
        }
    )
}

private fun formatLaTeXHtml(latex: String): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="utf-8">
          <meta name="viewport" content="width=device-width">
          <title>MathJax example</title>
          <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
          <script id="MathJax-script" async
                  src="https://cdn.jsdelivr.net/npm/mathjax@3.0.1/es5/tex-mml-chtml.js">
          </script>
        </head>
        <body>
        <p>
          When \(a \ne 0\), there are two solutions to \(ax^2 + bx + c = 0\) and they are
          \[x = {-b \pm \sqrt{b^2-4ac} \over 2a}.\]
        </p>
        </body>
        </html>
    """.trimIndent()
}

