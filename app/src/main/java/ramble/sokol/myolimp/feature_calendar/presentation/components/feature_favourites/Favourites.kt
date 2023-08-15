package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
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
            Image (
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_calendar_no_plans),
                contentDescription = "no plans",
                alignment = Alignment.Center
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
