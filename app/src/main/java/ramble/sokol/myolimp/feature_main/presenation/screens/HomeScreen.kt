package ramble.sokol.myolimp.feature_main.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.CalendarScreenDestination
import ramble.sokol.myolimp.destinations.CreateCalendarScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_main.presenation.components.CompletedPlanItem
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.White
import java.time.LocalDate

@Destination
@Composable
fun HomeScreen(
    navController: NavController
) {

    val viewModel = getViewModel<PlansViewModel>()
    val state by viewModel.state.collectAsState()

    val currentPlans = state.plans.filter {
        it.date == LocalDate.now().toString()
    }


    BottomBarTheme(
        navController = navController
    ) {
        // Today Plans

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    White,
                    RoundedCornerShape(25.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.today_plan),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = BlackProfile,
                        letterSpacing = 0.36.sp,
                    )
                )

                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            // update destination
                            navController.navigate(CalendarScreenDestination) {
                                popUpTo(NavGraphs.root) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            // show creating sheet
                            navController.navigate(CreateCalendarScreenDestination)
                        },
                    painter = painterResource(id = R.drawable.ic_main_add_plan),
                    contentDescription = "add plan",
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (currentPlans.isEmpty()) {

                ImageWithText (
                    drawable = R.drawable.ic_main_no_plans,
                    text = "У вас еще нет планов"
                )

            } else {
                currentPlans.forEach {
                    CompletedPlanItem(
                        item = it,
                        onComplete = { plan->
                            viewModel.onEvent(Event.OnCompletedPlan(plan = plan))
                        }
                    )
                }
            }

        }

    }
}