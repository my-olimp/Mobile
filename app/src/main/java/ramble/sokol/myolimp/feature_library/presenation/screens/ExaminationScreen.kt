package ramble.sokol.myolimp.feature_library.presenation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.states.TaskState
import ramble.sokol.myolimp.feature_library.domain.view_models.ArticleViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.article.ExaminationTask
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.regularType



@Composable
fun ExaminationScreen(
    viewModel: ArticleViewModel,
    blockId: Int
) {

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 18.dp)
    ) {
        Text(
            text = state.value.article.title,
            style = regularType(
                letterSpacing = 0.24.sp,
                fontSize = 12.sp,
                color = GreyProfileData
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        LazyColumn {
            items(state.value.article.blocks[blockId].questions.size) {
                with(state.value.article.blocks[blockId].questions[it]) {
                    ExaminationTask(
                        taskText = questionText,
                        taskLabel = stringResource(id = R.string.article_answer_type_float),
                        taskNumber = it.plus(1),
                        taskState = state.value.answers[id] ?: TaskState(),
                        explanationText = explanation,
                        onTextChanged = { newValue ->
                            viewModel.onEvent(ArticleEvent.OnAnswerTyped(
                                taskId = id,
                                taskNum = it,
                                answer = newValue,
                                blockId = blockId
                            ))
                            Log.i("ViewModelArticle","new value is : ${state.value.answers[id]}")
                        }
                    ) {
                        viewModel.onEvent(ArticleEvent.OnCheckAnswer(
                            taskId = id,
                            blockId = blockId
                        ))
                    }
                }
            }
        }
    }
}
