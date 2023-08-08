package ramble.sokol.myolimp.feature_calendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.feature_calendar.models.CalendarInput
import ramble.sokol.myolimp.feature_calendar.utils.createCalendarList

@Composable
fun CalendarImpl (

) {

    val calendarInputList by remember {
        mutableStateOf(createCalendarList())
    }

    var clickedCalendarItem by remember {
        mutableStateOf<CalendarInput?>(null)
    }

    CalendarObject (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .aspectRatio(1f),
        calendarInput = calendarInputList,
        onDateClicked = { day->
                clickedCalendarItem = calendarInputList.first {
                    it.day == day
                }
            },
        month = "June"
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        clickedCalendarItem?.toDos?.forEach {
            Text(text = it)
        }
    }
}
