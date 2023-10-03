package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun Explanation(
    isVisible: Boolean,
    solutionText: String
) {
    if(isVisible) {
        Column (modifier = Modifier.padding(vertical = 12.dp)){
            HorizontalLine()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.article_question_solution),
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 14.sp,
                    letterSpacing = 0.28.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = solutionText)

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalLine()
        }
    }
}