package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import java.time.LocalDate

@Composable
fun CurrentDay (
    currentDate: MutableState<LocalDate>,
    state: PlanState,
    onEvent: (Event) -> Unit
) {

    val currentPlans = state.plans.filter {
        it.date == currentDate.value.toString()
    }

    if (currentPlans.isEmpty()) {

        ImageWithText (
            "Планов нет..."
        )

    } else {
        currentPlans.forEach {
            PlanItem(
                item = it
            ) { plan->
                onEvent(Event.DeletePlan(plan))
            }
        }
    }


}
