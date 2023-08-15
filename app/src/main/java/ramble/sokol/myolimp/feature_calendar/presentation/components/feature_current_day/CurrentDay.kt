package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
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

    Column {
        if (currentPlans.isEmpty()) {

            Image (
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_calendar_no_plans),
                contentDescription = "no plans",
                alignment = Alignment.Center
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

}
