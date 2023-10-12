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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.article.SubjectItem
import ramble.sokol.myolimp.ui.theme.BlueStart
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
        modifier = Modifier
            .padding(bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ){
        Image(
            painter = painterResource(id = R.drawable.library_test_image),
            contentDescription = "test image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(120.dp)
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
            MarkDown()
        }
    }
}

@Preview
@Composable
fun MarkDown() {

    val text = """
# Алгоритм Дейкстры

> *Программирование калечит мозг, поэтому обучение ему должно трактоваться как преступление.*
Эдсгер Вибе Дейкстра
> 

**Алгоритм Дейкстры** — алгоритм нахождения кратчайших путей от данной вершины s до всех остальных во взешенном графе (V,E) с неотрицательными весами.

**Асимптотика**: 

|E| ≈ |V|^2

O(\,|E| · log(\,|V |\,)\,) - реализация \; для \; разреженных \;  графов

## Постановка задачи

Пусть дан взвешенный граф *(V, E)* с неотрицательными весами и вершина s. Необходимо найти кратчайшие пути от s до всех остальных вершин.
Удобнее всего представлять граф либо в виде матрицы смежности (где отсутствие ребра будет заменяться ребром длины *INF*), либо в виде списка смежности. В статье будет использоваться именно список.
Список смежности будет выглядеть как вектор векторов пар, где первый элемент в паре обозначает, куда ведёт ребро, а второй — его стоимость.

## Алгоритм

Заведём массив *d* длины *|V|*, где *d[i]* будет равен кратчайшему расстоянию от *s* до *i*. Изначально *d* заполнен *INF*, а *d[s] = 0*.
Затем запустим цикл, который будет пытаться оптимизировать расстояние. Каждый раз он будет искать ближайшую необработанную вершину *i* и пытаться укоротить расстояние до его соседей. То есть для ребра *j:* 

d [j.first] = min (d[j.first], d[i] + d[j.second]).

Искать ближайшую вершину можно либо простым перебором, либо ис- пользуя такие типы данных как *set* и *priority_queue*. Проблема последнего способа заключается в том, что их обновление происходит за *O(log(N))*, и если это происходит часто, то скорость сильно теряется. Поэтому, если граф плотный, то есть

|E| ≈ |V|^2

то лучше использовать базовую реализацию, иначе через *set*.

## Базовая реализация (C++)

```cpp
int N=V.size(); 
vector <int> d(N, INF);
d[s] = 0;
vector <bool> visited (N, false ); 
while (1) {
	int mn = INF, id = −1;
	for (int i = 0; i <N; ++i) if (!visited[i] &&d[i] <mn) {
		mn = d [ i ] ; 
		id = i;
	}
	if (id == −1) break;
	visited[id] = true; 
	for (auto i: V[id])
		if (!visited[i.first] && d[i.first] > d[id] + d[i.second])
			 d[i.first] = d[id] + d[i.second];
}
```

## Восстановление путей

Чтобы восстановить кратчайшие пути, нужно хранить в массиве p предыдущие вершины.

```cpp
int N=V.size();
vector <int> d(N, INF), p(N, −1);
d[s] = 0;
vector <bool> visited (N, false ); 
while (1) {
	int mn = INF, id = −1;
	for (int i = 0; i <N; ++i) if (!visited[i] &&d[i] <mn) {
			mn = d [ i ]; 
			id = i;
}
if (id == −1) break;
visited[id] = true;
for (auto i: V[id])
	if (!visited[i.first] && d[i.first] > d[id] + d[i.second]) {
		d[i.first] = d[id] + d[i.second];
		p[i.first] = id;
}
```

## Модификация для разреженных графов

Если в графе относительно немного рёбер, то кратчайшие расстояния можно дублировать в *set ds*, чтобы быстро *(O(log(N))* вместо *O(N)* получать ближайшую необработанную вершину. При этом обновление расстояния будет происходить медленно: *O (log(N))* вместо *O(1)*
    """

    MarkdownText(
        modifier = Modifier
            .fillMaxWidth(),
        markdown = text,
        color = SheetTitle,
        fontSize = 12.sp,
        linkColor = BlueStart,
        textAlign = TextAlign.Start,
        lineHeight = 16.sp,
        isTextSelectable = true,
        fontResource = R.font.medium,

        )
}

