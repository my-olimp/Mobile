package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.CalendarFocusedText
import ramble.sokol.myolimp.ui.theme.CalendarUnFocusedText
import ramble.sokol.myolimp.ui.theme.CheckboxUnselectedColor
import ramble.sokol.myolimp.ui.theme.CloseIconColor
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun CheckListBottomSheet(
    subjects: HashMap<String,Boolean>,
    onChooseItem: (String,Boolean) -> Unit,
    onFilter: () -> Unit,
    onHideSheet: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.subject),
                style = mediumType(
                    color = CalendarUnFocusedText,
                    fontSize = 18.sp
                ),
            )
            IconButton(
                onClick = {
                    onHideSheet()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close bottom sheet",
                    tint = CloseIconColor
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            subjects.forEach { (subjectTitle,checked) ->
                Row(
                   // modifier = Modifier.clip(RoundedCornerShape(3.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { onChooseItem(subjectTitle,it) },
                        modifier = Modifier.size(24.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = CalendarFocusedText,
                            uncheckedColor = CheckboxUnselectedColor,
                            checkmarkColor = White,
                        )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = subjectTitle,
                        style = regularType(
                            color = SheetTitle,
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        FilledBtn(
            text = stringResource(R.string.button_show_text),
            //isEnabled = subjects.any { it.value } //At least one true
        ) {
            onHideSheet()
            onFilter()
        }
    }
}
