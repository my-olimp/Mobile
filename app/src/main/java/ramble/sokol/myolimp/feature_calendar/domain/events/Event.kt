package ramble.sokol.myolimp.feature_calendar.domain.events

import androidx.navigation.NavController
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel

sealed interface Event {
    data object CancelSearching: Event
    data object GetPreviousDate: Event
    data class OnTitleUpdated(val title: String): Event
    data class OnDateUpdated(val date: String): Event
    data class OnSubjectUpdated(val subject: String): Event
    data class OnColorUpdated(val color: String): Event
    data class OnSearchQueryUpdated(val searchQuery: String): Event
    data class OnTypeUpdated(val type: String): Event
    data class OnStartHourUpdated(val startHour: Int): Event
    data class OnStartMinUpdated(val startMin: Int): Event
    data class OnEndHourUpdated(val endHour: Int): Event
    data class OnEndMinUpdated(val endMin: Int): Event
    data class OnFavouriteClick(val isFavourite: Boolean): Event
    data class OnFavouritesShowing(val isShowing: Boolean): Event
    data class OnDatePickerShowing(val isShowing: Boolean): Event
    data class CreatePlan(
        val navController: NavController
    ): Event
    data class UpdatePlan(
        val navController: NavController,
        val id: Int
    ): Event
    data class DeletePlan(
        val plan: PlanModel
    ): Event
    data class IsShowingCreatedPlan(
        val isShowing: Boolean
    ): Event
    data class OnCompletedPlan(
        val plan: PlanModel,
        val isCompleted: Boolean
    ): Event
    data class SaveDate(
        val date: String
    ): Event
}