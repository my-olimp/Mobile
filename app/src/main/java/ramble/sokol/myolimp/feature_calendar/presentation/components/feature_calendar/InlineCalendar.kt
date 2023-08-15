package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.dayViewModifier
import java.time.LocalDate
import java.time.YearMonth

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

    val itemWidth = LocalConfiguration.current.screenWidthDp / 7

    CalendarPager(
        loadedDates = loadedDates,
        loadNextDates = loadNextWeek,
        loadPrevDates = loadPrevWeek
    ) { currentPage ->
        Row {
            loadedDates[currentPage]
                .forEach { date ->
                    Box(
                        modifier = Modifier
                            .width(itemWidth.dp)
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
