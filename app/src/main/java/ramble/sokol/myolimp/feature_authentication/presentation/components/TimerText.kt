package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.regularType
import kotlin.time.Duration.Companion.seconds

@Composable
fun MinuteTimerText(
    textIdDisabled: Int = R.string.resend_code_again_disabled,
    textIdEnabled: Int = R.string.resend_code_again,
    timerValue: Int = 60,
    needToUpdate: Boolean = false,
    onClick: () -> Unit
) {

    var update by remember { mutableStateOf(needToUpdate) }
    var remainingTicks by remember { mutableIntStateOf(timerValue) }
    var isEnabled by remember { mutableStateOf(false) }

    if(needToUpdate != update) {
        update = !update
        remainingTicks = timerValue
    }

    LaunchedEffect(remainingTicks){
        while(remainingTicks > 0) {
            delay(duration = 1.seconds)
            remainingTicks--
        }
        if(remainingTicks == 0) isEnabled = true
    }

    Text(
        text = timerString(
            remainTicks = remainingTicks,
            idDisabled = textIdDisabled,
            idEnabled = textIdEnabled
        ),
        textAlign = TextAlign.Center,
        style = regularType(color = BlackProfile),
        modifier = if(remainingTicks == 0) {
            Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onClick() } }
        else Modifier
    )
}

@Composable
private fun timerString(remainTicks: Int,idDisabled: Int,idEnabled: Int): String {
    return when {
        remainTicks > 0 -> {
            stringResource(id = idDisabled, remainToString(remainTicks))
        }
        else -> {
             stringResource(id = idEnabled)
        }
    }
}

private fun remainToString(remain: Int): String {
    return "${remain.div(60)}:${remain.mod(60).div(10)}${remain.mod(60).mod(10)}"
}