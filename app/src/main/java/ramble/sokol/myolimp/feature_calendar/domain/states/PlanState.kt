package ramble.sokol.myolimp.feature_calendar.domain.states

import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel

data class PlanState (
    val plans: List<PlanModel> = emptyList(),

    val title: String = "",
    val date: String = "",
    val subject: String = "",
    val color: String = "",
    val type: String = "Событие",
    val isFavourite: Boolean = false,

    val startHour: Int = 0,
    val startMinute: Int = 0,
    val endHour: Int = 0,
    val endMinute: Int = 0,

    val isAddingPlan: Boolean = false,
    var isSearching: Boolean = false,
    val isShowingFavourites: Boolean = false,

    val searchQuery: String = ""
)
