package ramble.sokol.myolimp.feature_calendar.models

data class CalendarInput(
    val day: Int,
    val toDos: List<String> = emptyList()
)
