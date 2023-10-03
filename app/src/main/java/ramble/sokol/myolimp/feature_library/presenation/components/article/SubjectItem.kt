package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.regularType

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