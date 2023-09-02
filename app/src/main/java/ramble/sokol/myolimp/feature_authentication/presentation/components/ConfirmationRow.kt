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
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.presentation.screens.RegisterInfoScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConfirmationRow (
    onEvent: (SignUpEvent) -> Unit,
    navigator: DestinationsNavigator,
    isError: Boolean
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
            isError = isError,
            onTextChanged = {
                onEvent(SignUpEvent.OnCode1Updated(
                    navigator,
                    it
                ))
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
            isError = isError,
            onTextChanged = {
                onEvent(SignUpEvent.OnCode2Updated(
                    navigator,
                    it
                ))
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
            isError = isError,
            onTextChanged = {
                onEvent(SignUpEvent.OnCode3Updated(
                    navigator,
                    it
                ))
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
            isError = isError,
            onTextChanged = {
                onEvent(SignUpEvent.OnCode4Updated(
                    navigator,
                    it
                ))
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
            isError = isError,
            onTextChanged = {
                onEvent(SignUpEvent.OnCode5Updated(
                    navigator,
                    it
                ))
                focusManager.moveFocus(FocusDirection.Next)
            }
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        ConfirmationCode(
            modifier = Modifier
                .weight(0.16f)
                .focusRequester(code6),
            placeholder = "",
            isError = isError,
            onTextChanged = {
                focusManager.clearFocus()
                onEvent(SignUpEvent.OnCode6Updated(
                    navigator,
                    it
                ))
            }
        )
    }
}

