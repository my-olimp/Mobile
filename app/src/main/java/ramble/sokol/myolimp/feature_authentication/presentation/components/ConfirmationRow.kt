package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.utils.CodePart
import kotlin.time.Duration.Companion.seconds

@Composable
fun ConfirmationRow (

) {
    val coroutineScope = rememberCoroutineScope()

    var selectedPart by remember {
        mutableStateOf(CodePart.CODE_1)
    }

    var code1st by remember {
        mutableIntStateOf(1)
    }
    var code2nd by remember {
        mutableIntStateOf(2)
    }
    var code3rd by remember {
        mutableIntStateOf(3)
    }
    var code4th by remember {
        mutableIntStateOf(4)
    }
    var code5th by remember {
        mutableIntStateOf(5)
    }
    var code6th by remember {
        mutableIntStateOf(6)
    }

    val selectedCode by remember {
        derivedStateOf {
            when (selectedPart) {
                CodePart.CODE_1 -> code1st
                CodePart.CODE_2 -> code2nd
                CodePart.CODE_3 -> code3rd
                CodePart.CODE_4 -> code4th
                CodePart.CODE_5 -> code5th
                CodePart.CODE_6 -> code6th
            }
        }
    }

    val onTyped: (Int) -> Unit = remember {
        {
            when (selectedPart) {

                CodePart.CODE_1 -> {
                    code1st = it
                    // next part
                    coroutineScope.launch {
                        delay(0.5.seconds)
                        selectedPart = CodePart.CODE_2
                    }
                }
                CodePart.CODE_2 -> {
                    code2nd = it
                    // next part
                    coroutineScope.launch {
                        delay(0.5.seconds)
                        selectedPart = CodePart.CODE_3
                    }
                }
                CodePart.CODE_3 -> {
                    code3rd = it
                    // next part
                    coroutineScope.launch {
                        delay(0.5.seconds)
                        selectedPart = CodePart.CODE_4
                    }
                }
                CodePart.CODE_4 -> {
                    code4th = it
                    // next part
                    coroutineScope.launch {
                        delay(0.5.seconds)
                        selectedPart = CodePart.CODE_5
                    }
                }
                CodePart.CODE_5 -> {
                    code5th = it
                    // next part
                    coroutineScope.launch {
                        delay(0.5.seconds)
                        selectedPart = CodePart.CODE_6
                    }
                }
                CodePart.CODE_6 -> {
                    code6th = it
                }
            }
        }
    }

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code1st,
            isSelected = selectedPart == CodePart.CODE_1,
            onTextChanged = {},
            onClick = {}
        )

        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code2nd,
            isSelected = selectedPart == CodePart.CODE_2,
            onTextChanged = {},
            onClick = {}
        )

        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code3rd,
            isSelected = selectedPart == CodePart.CODE_3,
            onTextChanged = {},
            onClick = {}
        )

        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code4th,
            isSelected = selectedPart == CodePart.CODE_4,
            onTextChanged = {},
            onClick = {}
        )

        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code5th,
            isSelected = selectedPart == CodePart.CODE_5,
            onTextChanged = {},
            onClick = {}
        )

        ConfirmationCode(
            modifier = Modifier
                .weight(0.15f),
            text = code6th,
            isSelected = selectedPart == CodePart.CODE_6,
            onTextChanged = {},
            onClick = {}
        )
    }

}
