package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem

@Composable
fun Favourites (
    state: PlanState,
    navController: NavController,
) {

    val favouritesPlans = state.plans
        .filter {
            it.isFavourite
        }
        .sortedBy {
            it.date
        }
        .sortedBy {
            it.startHour
        }

    if (favouritesPlans.isEmpty()) {

        ImageWithText (
            drawable = R.drawable.ic_calendar_no_plans,
            text = stringResource(R.string.no_favourites)
        )

    } else {

        favouritesPlans.forEach {
            PlanItem (
                item = it,
                onEdit = { plan ->
                    navController.navigate(
                        UpdateScreenDestination(plan = plan)
                    )
                }
            )
        }
    }
}
