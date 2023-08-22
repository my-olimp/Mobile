package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
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
fun Favourites (
    state: PlanState,
    navController: NavController,
) {

    val favouritesPlans = state.plans
        .filter {
            it.isFavourite
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

    if (favouritesPlans.isEmpty()) {

        ImageWithText (
            drawable = R.drawable.ic_calendar_no_plans,
            text = stringResource(R.string.no_favourites)
        )

    } else {

        val rightNow = Calendar.getInstance()
        val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23

        val nextPlans = favouritesPlans
            .filter {
                LocalDate.parse(it.date).isAfter(LocalDate.now())
                        || (LocalDate.parse(it.date).isEqual(LocalDate.now()) && it.startHour > currentHour)
            }

        val previousPlans = favouritesPlans
            .filter {
                LocalDate.parse(it.date).isBefore(LocalDate.now())
                        || (LocalDate.parse(it.date).isEqual(LocalDate.now()) && it.startHour <= currentHour)
            }
        
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(text = "${favouritesPlans.size} - ${previousPlans.size} - ${nextPlans.size}")
            
            // show next
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
        }
    }
}
