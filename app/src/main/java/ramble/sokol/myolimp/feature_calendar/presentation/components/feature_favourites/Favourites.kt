package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites

import androidx.compose.runtime.Composable
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem

@Composable
fun Favourites (
    state: PlanState,
    onEvent: (Event) -> Unit
) {
    val favouritesPlans = state.plans.filter {
        it.subject.contains(state.searchQuery, ignoreCase = true) ||
                it.title.contains(state.searchQuery, ignoreCase = true)
    }

    if (favouritesPlans.isEmpty()) {

        ImageWithText (
            "Здесь пока пусто :("
        )

    } else {

        favouritesPlans.forEach {
            PlanItem (
                item = it,
                onDelete = { plan ->
                    onEvent(Event.DeletePlan(plan = plan))
                }
            )
        }
    }
}
