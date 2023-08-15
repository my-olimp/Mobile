package aleshka.developement.calendarapp.presentation.component.feature_calendar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ToggleExpandCalendarButton(
    isExpanded: Boolean,
    expand: () -> Unit,
    collapse: () -> Unit
) {

    /*
        toggle calendar button
        change state after click
    */

    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = {
                isChecked ->
            if (isChecked) expand()
            else collapse()
        }
    ) {

        /*
            if expanded, calendar is collapsed
            unless it is expanded
        */

        if (isExpanded) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                "Collapse calendar",
                tint = Color(0xFF222222)
            )
        } else {
            Icon(
                Icons.Default.KeyboardArrowDown,
                "Expand calendar",
                tint = Color(0xFF222222)
            )
        }

    }
}