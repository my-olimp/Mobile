package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun EducationTask(
    taskText: String = ""
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
                text = taskText,
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 11.sp,
                    letterSpacing = 0.22.sp
                )
            )
        }
    }
}