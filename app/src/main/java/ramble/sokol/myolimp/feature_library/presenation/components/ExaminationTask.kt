package ramble.sokol.myolimp.feature_library.presenation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.ErrorMessage
import ramble.sokol.myolimp.feature_library.domain.states.TaskState
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun ExaminationTask(
    taskText: String,
    taskLabel: String,
    taskNumber: Int,
    taskState: TaskState,
    onTextChanged: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {

        Text(
            text = stringResource(id = R.string.article_task_number,taskNumber),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.bold)),
                fontWeight = FontWeight(500),
                color = SheetTitle
            )
        )
        Text(
            text = taskText,
            style = mediumType(
                fontSize = 14.sp,
                color = SheetTitle,
                letterSpacing = 0.28.sp
            )
        )
        OutlinedText(
            previousData = taskState.answer,
            isError = taskState.isError,
            label = taskLabel,
            onTextChanged = {
                onTextChanged(it)
            }
        )
        Log.i("ViewModelArticle","current state in task is: $taskState")
        if(taskState.isError) {
            ErrorMessage(text = stringResource(id = R.string.article_question_error))
        }
        if(taskState.isSuccess) {
            /*TODO somithing*/
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnswerBtn(text = stringResource(id = R.string.answer)) {
            onButtonClick()
        }
    }
}