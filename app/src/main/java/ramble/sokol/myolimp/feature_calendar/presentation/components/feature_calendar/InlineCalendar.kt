package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.dayViewModifier
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun InlineCalendar(
    loadedDates: Array<List<LocalDate>>,
    selectedDate: LocalDate,
    currentMonth: YearMonth,
    loadNextWeek: (nextWeekDate: LocalDate) -> Unit,
    loadPrevWeek: (endWeekDate: LocalDate) -> Unit,
    onDayClick: (LocalDate) -> Unit,
    state: PlanState
) {

    /*
        it shows when calendar is collapsed
    */

//    val itemWidth = LocalConfiguration.current.screenWidthDp / 7

    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = loadNextWeek,
        loadPrevDates = loadPrevWeek
    ) { currentPage ->

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            loadedDates[currentPage].forEach { date ->
                Box(
                    modifier = Modifier
//                            .width(itemWidth.dp)
                        .fillMaxWidth(0.14f)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DayView(
                        date,
                        isSelected = selectedDate == date,
                        onDayClick = onDayClick,
                        modifier = Modifier.dayViewModifier(date, currentMonth),
                        state = state,
                    )
                }
            }
        }
    }
}
