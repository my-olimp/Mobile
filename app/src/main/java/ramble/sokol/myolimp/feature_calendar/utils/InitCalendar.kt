package ramble.sokol.myolimp.feature_calendar.utils

import ramble.sokol.myolimp.feature_calendar.models.CalendarInput

fun createCalendarList() : List<CalendarInput> {

    val calendarInputs = mutableListOf<CalendarInput>()

    for (i in 1..31) {
        calendarInputs.add(
            CalendarInput(
                day = i,
                toDos = listOf(
                    "1st task",
                    "2nd task",
                    "3rd task"
                )
            )
        )
    }

    return calendarInputs
}
