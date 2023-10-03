package ramble.sokol.myolimp.feature_library.presenation.components.article

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun ExaminationTask(
    taskText: String,
    taskLabel: String,
    taskNumber: Int,
    taskState: TaskState,
    explanationText: String,
    onTextChanged: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {

        var isExpanded by remember {
            mutableStateOf(false)
        }

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
            isSuccess = taskState.isSuccess,
            label = taskLabel,
            onTextChanged = {
                onTextChanged(it)
            }
        )
        Log.i("ViewModelArticle","current state in task is: $taskState")
        if(taskState.isError) {
            ErrorMessage(text = stringResource(id = R.string.article_question_error))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            AnswerBtn(text = stringResource(id = R.string.answer)) {
                onButtonClick()
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            if(taskState.isError) {
                Text(
                    text = stringResource(id = R.string.article_question_show_answer),
                    style = regularType(
                        fontSize = 12.sp,
                        color = GreyProfileData,
                        letterSpacing = 0.24.sp
                    ),
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }
        }
        if(taskState.isError) Explanation(isVisible = isExpanded, solutionText = explanationText)
    }
}