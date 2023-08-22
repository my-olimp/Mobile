package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.CommentText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem
import java.time.LocalDate
import java.util.Calendar

@Composable
fun Searching (
    state: PlanState,
    navController: NavController,
) {

    val searchingPlans = state.plans
        .filter {
            it.subject.contains(state.searchQuery, ignoreCase = true) ||
                    it.title.contains(state.searchQuery, ignoreCase = true)
        }
        .sortedWith(
            compareBy(
                {
                    it.date
                },
                {
                    it.startHour
                },
                {
                    it.startMinute
                }
            )
        )

    if (searchingPlans.isEmpty()) {

        ImageWithText(
            drawable = R.drawable.ic_calendar_no_plans,
            text = stringResource(R.string.nothing_was_found, state.searchQuery)
        )

    } else {


        val rightNow = Calendar.getInstance()
        val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23

        val nextPlans = searchingPlans
            .filter {
                LocalDate.parse(it.date).isAfter(LocalDate.now())
                        || (LocalDate.parse(it.date)
                    .isEqual(LocalDate.now()) && it.startHour > currentHour)
            }

        val previousPlans = searchingPlans
            .filter {
                LocalDate.parse(it.date).isBefore(LocalDate.now())
                        || (LocalDate.parse(it.date)
                    .isEqual(LocalDate.now()) && it.startHour <= currentHour)
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // show next
            if (nextPlans.isNotEmpty()) {

                CommentText(
                    text = stringResource(R.string.next_events)
                )

                nextPlans.forEach {
                    PlanItem(
                        item = it,
                    ) { plan ->
                        navController.navigate(
                            UpdateScreenDestination(plan = plan)
                        )
                    }
                }
            }

            // show previous
            if (previousPlans.isNotEmpty()) {

                CommentText(
                    text = stringResource(R.string.previous_events)
                )

                previousPlans.forEach {
                    PlanItem(
                        item = it,
                    ) { plan ->
                        navController.navigate(
                            UpdateScreenDestination(plan = plan)
                        )
                    }
                }
            }
        }
    }
}
