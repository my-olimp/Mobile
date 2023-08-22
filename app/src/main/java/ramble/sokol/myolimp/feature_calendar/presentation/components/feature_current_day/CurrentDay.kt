package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@Composable
fun CurrentDay (
    currentDate: MutableState<LocalDate>,
    state: PlanState,
    navController: NavController
) {

    timeInFrame(
        startHour = 15,
        startMin = 0,
        endHour = 15,
        endMin = 45
    )

    timeInFrame(
        startHour = 23,
        startMin = 0,
        endHour = 23,
        endMin = 45
    )

    timeInFrame(
        startHour = 22,
        startMin = 0,
        endHour = 22,
        endMin = 45
    )

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
            text = stringResource(R.string.no_plans)
        )

    } else {

        // today
        if (currentDate.value == LocalDate.now()) {

            val rightNow = Calendar.getInstance()
            val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23
            val currentMin: Int = rightNow.get(Calendar.MINUTE) // 0..60

            val nextPlans = currentPlans
                .filter {
                    it.startHour > currentHour
                        || (it.endHour == 0 && it.endMinute == 0)
                            || (it.startHour == currentHour && it.startMinute > currentMin)
                }

            val previousPlans = currentPlans
                .filter {
                    it.endHour < currentHour && (it.endHour != it.endMinute && it.endHour != 0)
                        || (it.endHour == currentHour && it.endMinute < currentMin)
                }

            val curPlans = currentPlans
                .filter {
                    (it.endHour == currentHour && it.endMinute <= currentMin)
                            || (it.startHour == currentHour && it.startMinute >= currentMin)
                }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if (curPlans.isNotEmpty()) {

                    CommentText(
                        text = stringResource(R.string.current_events)
                    )

                    curPlans.forEach {
                        PlanItem(
                            item = it,
                        ) { plan ->
                            navController.navigate(
                                UpdateScreenDestination(plan = plan)
                            )
                        }
                    }
                }

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
                Spacer(modifier = Modifier.height(24.dp))

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

            // before today
        } else if (currentDate.value.isBefore(LocalDate.now())) {

            CommentText(
                text = stringResource(R.string.previous_events)
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
                text = stringResource(R.string.next_events)
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

@Composable
private fun timeInFrame(
    startHour: Int,
    startMin: Int,
    endHour: Int,
    endMin: Int,
) {

    val rightNow = Calendar.getInstance()
    val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23
    val currentMin: Int = rightNow.get(Calendar.MINUTE) // 0..60

    val differenceStartWithCurrent = differenceBetweenTime(
        start = "${addZeroIfNeeded(startHour)}:${addZeroIfNeeded(startMin)}",
        end = "${addZeroIfNeeded(currentHour)}:${addZeroIfNeeded(currentMin)}"
    )

    val differenceEndWithCurrent = differenceBetweenTime(
        start = "${addZeroIfNeeded(endHour)}:${addZeroIfNeeded(endMin)}",
        end = "${addZeroIfNeeded(currentHour)}:${addZeroIfNeeded(currentMin)}"
    )


    Log.i(PlansViewModel.TAG, "differenceStartWithCurrent - ${differenceStartWithCurrent.toMinutes()}")
    Log.i(PlansViewModel.TAG, "differenceEndWithCurrent - ${differenceEndWithCurrent.toMinutes()}")

    if (differenceStartWithCurrent.toMinutes() < 0L && differenceEndWithCurrent.toMinutes() < 0L) {
        // next

        Log.i(PlansViewModel.TAG, "next")

    } else if (differenceStartWithCurrent.toMinutes() > 0L && differenceEndWithCurrent.toMinutes() > 0L) {
        // previous

        Log.i(PlansViewModel.TAG, "previous")

    } else if ((differenceStartWithCurrent.toMinutes() > 0L && differenceEndWithCurrent.toMinutes() < 0L) || differenceStartWithCurrent.toMinutes() < 0L && differenceEndWithCurrent.toMinutes() > 0L) {
        // current

        Log.i(PlansViewModel.TAG, "current")

    } else {
        // warning

        Log.i(PlansViewModel.TAG, "warning")

    }

}

@Composable
private fun addZeroIfNeeded (
    num: Int
) : String {

    if (num.toString().length == 1) {
        return "0$num"
    }

    return num.toString()
}

@Composable
private fun differenceBetweenTime (
    start: String,
    end: String
): Duration {

    val startInclusive = LocalTime.parse(start)
    val endInclusive = LocalTime.parse(end)

    return Duration.between(startInclusive, endInclusive)
}

