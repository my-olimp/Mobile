package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.runtime.Composable
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem

@Composable
fun Searching (
    state: PlanState,
    onEvent: (Event) -> Unit
) {

    val searchingPlans = state.plans.filter {
        it.subject.contains(state.searchQuery, ignoreCase = true) ||
                it.title.contains(state.searchQuery, ignoreCase = true)
    }

    if (searchingPlans.isEmpty()) {

        ImageWithText (
            "По запросу '${state.searchQuery}' ничего не найдено"
        )

    } else {

        searchingPlans.forEach {
            PlanItem(
                item = it,
            ) { plan->
                onEvent(Event.DeletePlan(plan))
            }
        }

    }
}
