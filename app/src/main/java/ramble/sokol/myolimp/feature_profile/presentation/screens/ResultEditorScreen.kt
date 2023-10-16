package ramble.sokol.myolimp.feature_profile.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import dev.jeziellago.compose.markdowntext.MarkdownText
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SheetTitle

@Destination
@Composable
fun ResultEditorScreen(
    text: String
) {
    OlimpTheme(onReload = {

    }) {
        MarkdownText(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
}
