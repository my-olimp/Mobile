package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.feature_library.presenation.components.ExaminationTask
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.regularType


data class Task(
    val taskText: String,
    val taskAnswer: String
)


@Composable
fun ExaminationScreen() {

    val testItems = listOf(
        Task(taskText = "Задание 1\nРешите уравнение: x3 + 3x2 − 5x − 15 = 0;\nВ ответ запишите меньший корень", taskAnswer = "Число или дробь"),
        Task(taskText = "Задание 2\nРешите уравнение: x4 − 2x3 − 3x + 6 = 0;\nВ ответ запишите меньший корень", taskAnswer = "Число или дробь"),
        Task(taskText = "Задание 3 Решите уравнение: x4 − 2x3 − 3x + 6 = 0; В ответ запишите меньший корень", taskAnswer = "Число или дробь")
    )
    Column(
        modifier = Modifier.padding(18.dp)
    ) {
        Text(
            text = "Уравнение высших порядков",
            style = regularType(
                letterSpacing = 0.24.sp,
                fontSize = 12.sp,
                color = GreyProfileData
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            items(testItems.size) {
                ExaminationTask(
                    taskText = testItems[it].taskText,
                    taskLabel = testItems[it].taskAnswer,
                    onTextChanged = {/*TODO*/ }) {
                    /*TODO*/
                }
            }
        }
    }
}
