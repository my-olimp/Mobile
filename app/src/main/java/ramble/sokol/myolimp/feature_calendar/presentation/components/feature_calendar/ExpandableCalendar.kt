package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import aleshka.developement.calendarapp.presentation.component.feature_calendar.ToggleExpandCalendarButton
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.getWeekStartDate
import ramble.sokol.myolimp.feature_calendar.domain.view_models.CalendarViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching.SearchTextField
import ramble.sokol.myolimp.feature_calendar.presentation.core.CalendarIntent
import ramble.sokol.myolimp.feature_calendar.presentation.core.Period
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White
import java.time.LocalDate

/**
 * dayShape - to set shape
 * backgroundColor - to set background color of the calendar
 * selectedDayBackgroundColor - to set background color of the chosen day
 * dayValueTextColor - to set text color of the day
 * selectedDayValueTextColor - to set text color of chosen day
 * headerTextColor - to set text color of the title
 * weekDaysTextColor - to set text color of the weekdays
 * */

@Composable
fun ExpandableCalendar(
    state: PlanState,
    onDayClick: (LocalDate) -> Unit,
    onEvent: (Event) -> Unit
) {

    val viewModel: CalendarViewModel = viewModel()
    val loadedDates = viewModel.visibleDates.collectAsState()
    val selectedDate = viewModel.selectedDate.collectAsState()
    val calendarExpanded = viewModel.calendarExpanded.collectAsState()
    val currentMonth = viewModel.currentMonth.collectAsState()
    var isShouldFlipping by remember {
        mutableStateOf(true)
    }

    /*
        to set current day
    */
    if (state.isShowingCreatedPlan) {
        // choose day of created plan
        viewModel.onIntent(CalendarIntent.SelectDate(LocalDate.parse(state.date)))

        // show plans of chosen day
        onDayClick(LocalDate.parse(state.date))

        onEvent(
            Event.IsShowingCreatedPlan(false)
        )
    }
    // open month of creating plan
    if (
        (currentMonth.value.monthValue < LocalDate.parse(state.date).monthValue && isShouldFlipping)
        || (currentMonth.value.year < LocalDate.parse(state.date).year && isShouldFlipping)
    ) {

        // expand calendar
        viewModel.onIntent(CalendarIntent.ExpandCalendar)

        for(i in 0..(LocalDate.parse(state.date).monthValue - currentMonth.value.monthValue)
            + (LocalDate.parse(state.date).year - currentMonth.value.year) * 12) {
            viewModel.onIntent(
                CalendarIntent.LoadNextDates(
                    currentMonth.value.atDay(
                        1
                    ),
                    period = Period.MONTH
                )
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .animateContentSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        /*
         search & bookmarks
        */

        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Max)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {

            SearchTextField(
                state = state,
                onClearFocus = {
                    onEvent(Event.CancelSearching)
                },
                onTextChanged = {

                    viewModel.onIntent(CalendarIntent.CollapseCalendar)

                    onEvent(Event.OnSearchQueryUpdated(it))
                }
            )

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (state.isShowingFavourites) BlueStart else White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .clickable {
                        onEvent(Event.OnFavouritesShowing(!state.isShowingFavourites))
                    },
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_calendar_favourite),
                    contentDescription = "bookmark",
                    tint = if (state.isShowingFavourites) White else GreyProfileData,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .clickable(
                    onClick = {
                        onEvent(Event.CancelSearching)
                    }
                )
                .background(
                    color = White,
                    shape = RoundedCornerShape(
                        size = 8.dp
                    )
                )
        ) {

            /*
                month & toggle
            */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MonthText(
                    selectedMonth = currentMonth.value
                )

                ToggleExpandCalendarButton(
                    isExpanded = calendarExpanded.value,
                    expand = {
                        viewModel.onIntent(CalendarIntent.ExpandCalendar)
                    },
                    collapse = {
                        viewModel.onIntent(CalendarIntent.CollapseCalendar)
                    },
                )
            }

//            Spacer(modifier = Modifier.height(16.dp))

            if (calendarExpanded.value) {
                MonthViewCalendar(
                    loadedDates.value,
                    selectedDate.value,
                    currentMonth = currentMonth.value,
                    loadDatesForMonth = { yearMonth ->
                        viewModel.onIntent(
                            CalendarIntent.LoadNextDates(
                                yearMonth.atDay(
                                    1
                                ),
                                period = Period.MONTH
                            )
                        )

                        // change permission
                        isShouldFlipping = false
                    },
                    onDayClick = {
                        // to set date
                        // if this day before today, we will set today,
                        // unless set this day
//                        onEvent(Event.OnDateUpdated(
//                            if (!it.isBefore(LocalDate.now())) it.toString() else LocalDate.now().toString())
//                        )

                        onEvent(Event.OnDateUpdated(it.toString()))

                        viewModel.onIntent(CalendarIntent.SelectDate(it))
                        onDayClick(it)
                    },
                    state = state
                )
            } else {
                InlineCalendar(
                    loadedDates.value,
                    selectedDate.value,
                    loadNextWeek = { nextWeekDate ->
                        viewModel.onIntent(
                            CalendarIntent.LoadNextDates(nextWeekDate)
                        )

                        // change permission
                        isShouldFlipping = false
                    },
                    loadPrevWeek = { endWeekDate ->
                        viewModel.onIntent(
                            CalendarIntent.LoadNextDates(
                                endWeekDate.minusDays(1).getWeekStartDate()
                            )
                        )

                        // change permission
                        isShouldFlipping = false
                    },
                    onDayClick = {
                        // to set date
                        // if this day before today, we will set today,
                        // unless set this day
//                        onEvent(Event.OnDateUpdated(
//                            if (!it.isBefore(LocalDate.now())) it.toString() else LocalDate.now().toString())
//                        )

                        onEvent(Event.OnDateUpdated(it.toString()))

                        viewModel.onIntent(CalendarIntent.SelectDate(it))

                        onDayClick(it)
                    },
                    state = state,
                    currentMonth = currentMonth.value,
                )
            }
        }
    }
}
