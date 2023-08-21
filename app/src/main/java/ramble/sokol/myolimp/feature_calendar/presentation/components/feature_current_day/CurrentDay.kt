package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import java.time.LocalDate
import java.util.Calendar

@Composable
fun CurrentDay (
    currentDate: MutableState<LocalDate>,
    state: PlanState,
    navController: NavController
) {

    val currentPlans = state.plans
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

    if (currentPlans.isEmpty()) {

        ImageWithText (
            drawable = R.drawable.ic_calendar_no_plans,
            text = "Планов нет..."
        )

    } else {

        // today
        if (currentDate.value == LocalDate.now()) {

            val rightNow = Calendar.getInstance()
            val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val nextPlans = currentPlans
                    .filter {

                        Log.i(
                            PlansViewModel.TAG,
                            "$currentHour - ${it.startHour} - ${it.startHour < currentHour}"
                        )

                        it.startHour > currentHour
                    }

                if (nextPlans.isNotEmpty()) {

                    CommentText(
                        text = "Предстоящие события"
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


                val previousPlans = currentPlans
                    .filter {
                        Log.i(
                            PlansViewModel.TAG,
                            "$currentHour - ${it.endHour} - ${it.endHour < currentHour}"
                        )

                        it.endHour < currentHour
                    }

                if (previousPlans.isNotEmpty()) {

                    CommentText(
                        text = "Прошедшие события"
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
                Spacer(modifier = Modifier.height(24.dp))

//            currentPlans.forEach {
//                PlanItem(
//                    item = it,
//                ) { plan->
//                    navController.navigate(
//                        UpdateScreenDestination(plan = plan)
//                    )
//                }
//            }
            }

            // before today
        } else if (currentDate.value.isBefore(LocalDate.now())) {

            CommentText(
                text = "Прошедшие события"
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
        // after

        } else {
            CommentText(
                text = "Предстоящие события"
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
    }
}
