package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.EducationTask
import ramble.sokol.myolimp.feature_library.presenation.components.SubjectItem
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EducationScreen(
    viewModel: ArticleViewModel,
    blockId: Int
) {

    val state = viewModel.state.collectAsState()

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        /*TODO back hasn't image for test :/ */
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
                text = state.value.article.title,
                style = mediumType(
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(
                    id = R.string.article_author,
                    state.value.article.author.firstName,
                    state.value.article.author.secondName,
                    state.value.article.author.thirdName
                    ),
                style = regularType(
                    fontSize = 12.sp,
                    letterSpacing = 0.24.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                state.value.article.tags.forEach {
                    SubjectItem(subjectText = it)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            /*INTRODUCTION*/
            Text(
                text = "Методы решения уравнений третьей и четвёртой степени (формула Кардано и метод Феррари) выходят за рамки программы обычной школы. Поэтому если на олимпиаде вам попадается уравнение степени 3 или выше, то следует искать искусственный приём, приспособленный для решения именно этого уравнения. Таким приёмом может быть, например, удачная группировка с последующим разложением на множители или выявление устойчивых выражений с соответствующей заменой переменной. Данная статья посвящена уравнениям вида p(x) = 0, где p(x) — многочлен третьей степени и выше, и некоторым приёмам разложения такого многочлена на множители.\nНепосредственная группировка\nВ простейших случаях многочлен удаётся разложить на множители, удачно группируя друг с другом слагаемые.",
                style = regularType(
                    color = SheetTitle,
                    letterSpacing = 0.22.sp,
                    fontSize = 11.sp,
                    lineHeight = 15.62.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            /*TASKS*/
            EducationTask(
                taskText = "Задача 1. Решить уравнение 2x3 − 3x2 − 8x + 12 = 0.\nРешение:\nx2(2x − 3) − 4(2x − 3) = 0 ⇔ \n(2x − 3)(x2 − 4) = 0 ⇔ \n(2x − 3)(x − 2)(x + 2) = 0. \nОтвет: 3/2, ±2."
            )

            Spacer(modifier = Modifier.height(6.dp))

            EducationTask(
                taskText = "Задача 2. (МГУ, социологич. ф-т, 2004 ) Решите уравнение:\nx3 + 9x2 + 18x − 2(x 2 + 9x) − 36\n √ x + 3 \nРешение:\n Если поддаться искушению раскрыть скобки и привести подобные слагаемые (тем более что сократится 18x), то в возникшем кубическом уравнении придётся подбирать корень с целью разложить левую часть на множители. Данная процедура описана в следующем пункте и не представляет здесь никаких сложностей, однако необходимости в ней сейчас нет. Дело в том, что несколько вычурная запись условия содержит подсказку, как именно надо группировать слагаемые. Имеем: \nx3 + 9x2 + 18x − 2(x2 + 9x) − 36 = x(x2 + 9x) − 2(x2 + 9x) + 18x − 36 = (x 2 + 9x)(x − 2) + 18(x − 2) = (x − 2)(x2 + 9x + 18) = (x − 2)(x + 3)(x + 6).\nНаше уравнение, таким образом, равносильно системе\n (x − 2)(x + 3)(x + 6) = 0, \n x + 3 > 0, \nрешением которой служит x = 2.\nОтвет: 2. "
            )
        }
    }
}