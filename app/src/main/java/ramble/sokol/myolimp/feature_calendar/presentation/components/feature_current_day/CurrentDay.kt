package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.PlanTimeStatus
import ramble.sokol.myolimp.feature_calendar.domain.utils.getPlanTimeStatus
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import java.time.LocalDate

@Composable
fun CurrentDay (
    currentDate: MutableState<LocalDate>,
    state: PlanState,
    navController: NavController
) {

    val plans = state.plans
        .filter {
            it.date == currentDate.value.toString()
        }
        .sortedWith(
            compareBy(
                {
                    it.startHour
                },
                {
                    it.startMinute
                }
            )
        )

    if (plans.isEmpty()) {

        ImageWithText (
            drawable = R.drawable.ic_calendar_no_plans,
            text = stringResource(R.string.no_plans)
        )

    } else {

        // today
        if (currentDate.value == LocalDate.now()) {

            val nextPlans = plans
                .filter {
                    getPlanTimeStatus(
                        it.startHour,
                        it.startMinute,
                        it.endHour,
                        it.endMinute,
                    ) == PlanTimeStatus.NEXT
                }

            val previousPlans = plans
                .filter {
                    getPlanTimeStatus(
                        it.startHour,
                        it.startMinute,
                        it.endHour,
                        it.endMinute,
                    ) == PlanTimeStatus.PREVIOUS
                }

            val currentPlans = plans
                .filter {
                        getPlanTimeStatus(
                            it.startHour,
                            it.startMinute,
                            it.endHour,
                            it.endMinute,
                        ) == PlanTimeStatus.CURRENT
                    }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // show current
                if (currentPlans.isNotEmpty()) {

                    CommentText(
                        text = stringResource(R.string.current_events)
                    )

                    currentPlans.forEach {
                        PlanItem(
                            item = it,
                        ) { plan ->
                            navController.navigate(
                                UpdateScreenDestination(plan = plan)
                            )
                        }
                    }
                }

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

        } else {
            // before or after today

            plans.forEach {
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
