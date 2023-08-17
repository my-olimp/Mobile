package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem

@Composable
fun Searching (
    state: PlanState,
    navController: NavController
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
                navController.navigate(
                    UpdateScreenDestination(plan = plan)
                )
            }
        }

    }
}
