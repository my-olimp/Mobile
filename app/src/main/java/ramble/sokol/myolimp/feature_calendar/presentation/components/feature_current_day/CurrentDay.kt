package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import java.time.LocalDate

@Composable
fun CurrentDay (
    currentDate: MutableState<LocalDate>,
    state: PlanState,
    navController: NavController
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
                item = it,
            ) { plan->
                navController.navigate(
                    UpdateScreenDestination(plan = plan)
                )
            }
        }
    }

}
