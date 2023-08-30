package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConfirmationRow (
    onEvent: (SignUpEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val (
        code1,
        code2,
        code3,
        code4,
        code5,
        code6,
    ) = FocusRequester.createRefs()

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code1)
                .focusProperties {
                    next = code2
                },
            placeholder = "",
            onTextChanged = {
                onEvent(SignUpEvent.OnCode1Updated(it))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code2)
                .focusProperties {
                    next = code3
                },
            placeholder = "",
            onTextChanged = {
                onEvent(SignUpEvent.OnCode2Updated(it))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code3)
                .focusProperties {
                    next = code4
                },
            placeholder = "",
            onTextChanged = {
                onEvent(SignUpEvent.OnCode3Updated(it))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code4)
                .focusProperties {
                    next = code5
                },
            placeholder = "",
            onTextChanged = {
                onEvent(SignUpEvent.OnCode4Updated(it))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code5)
                .focusProperties {
                    next = code6
                },
            placeholder = "",
            onTextChanged = {
                onEvent(SignUpEvent.OnCode5Updated(it))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code6),
            placeholder = "",
            onTextChanged = {
                focusManager.clearFocus()
                onEvent(SignUpEvent.OnCode6Updated(it))
            }
        )
    }
}
