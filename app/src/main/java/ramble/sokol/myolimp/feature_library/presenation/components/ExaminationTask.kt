package ramble.sokol.myolimp.feature_library.presenation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun ExaminationTask(
    taskText: String,
    taskLabel: String,
    onTextChanged: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column {

        Text(
            text = taskText,
            style = mediumType(
                fontSize = 14.sp,
                color = SheetTitle,
                letterSpacing = 0.28.sp
            )
        )
        OutlinedText(
            previousData = "" /*TODO*/,
            label = taskLabel,
            onTextChanged = {
                onTextChanged(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnswerBtn(text = stringResource(id = R.string.answer)) {
            onButtonClick()
        }
    }
}