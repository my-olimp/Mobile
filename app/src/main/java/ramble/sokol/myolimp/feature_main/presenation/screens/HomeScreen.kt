package ramble.sokol.myolimp.feature_main.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.CreateCalendarScreenDestination
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.destinations.LibraryScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_main.data.models.AdviceArticle
import ramble.sokol.myolimp.feature_main.presenation.components.CompletedPlanItem
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.MainBackground
import ramble.sokol.myolimp.ui.theme.MainPageBlue
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

    val fragments = getFragments()

    val scroll = rememberScrollState()

    BottomBarTheme(
        navController = navController
    ) {

        // Today Plans
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
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
                Row(
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

                                // show creating sheet
                                navController.navigate(
                                    CreateCalendarScreenDestination(
                                        date = LocalDate
                                            .now()
                                            .toString(),
                                        fromHome = true
                                    )
                                ) {
                                    // not to save previous state - CreatePlan
                                    popUpTo(NavGraphs.root) {
                                        saveState = false
                                    }
                                    launchSingleTop = false
                                    restoreState = false
                                }

//                                // update destination
//                                navController.navigate(
//                                    HomeScreenDestination()
//                                ) {
//                                    popUpTo(NavGraphs.root) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
                            },
                        painter = painterResource(id = R.drawable.ic_main_add_plan),
                        contentDescription = "add plan",
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (currentPlans.isEmpty()) {

                    ImageWithText(
                        drawable = R.drawable.ic_main_no_plans,
                        text = "У вас еще нет планов"
                    )

                } else {
                    currentPlans.forEach {
                        CompletedPlanItem(
                            item = it,
                            onComplete = { plan, isCompleted ->
                                viewModel.onEvent(Event.OnCompletedPlan(
                                    plan = plan,
                                    isCompleted = isCompleted
                                ))
                            }
                        )
                    }
                }

            }

            // 1st step
            Column(
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.first_step),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = BlackProfile,
                            letterSpacing = 0.36.sp,
                        )
                    )

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                // update destination
                                navController.navigate(LibraryScreenDestination) {
                                    popUpTo(NavGraphs.root) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.nav_library_name),
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.medium)),
                                fontWeight = FontWeight(500),
                                color = MainPageBlue,
                                letterSpacing = 0.26.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.ic_main_next_step),
                            contentDescription = "open library"
                        )
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scroll)
                ) {
                    fragments.forEach {
                        Column(
                            modifier = Modifier
                                .size(120.dp)
                                .padding(end = 8.dp)
                                .paint(
                                    painterResource(R.drawable.ic_main_background_blue),
                                    contentScale = ContentScale.FillBounds
                                )
                                .padding(all = 12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = MainBackground,
                                            shape = RoundedCornerShape(size = 4.dp)
                                        )
                                        .padding(4.dp),
                                    text = it.type,
                                    style = TextStyle(
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily(Font(R.font.medium)),
                                        fontWeight = FontWeight(400),
                                        color = White,
                                    )
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = MainBackground,
                                            shape = RoundedCornerShape(size = 4.dp)
                                        )
                                        .padding(4.dp),
                                    text = it.subject,
                                    style = TextStyle(
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily(Font(R.font.medium)),
                                        fontWeight = FontWeight(400),
                                        color = White,
                                    )
                                )
                            }


                            Text(
                                text = it.title,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.bold)),
                                    fontWeight = FontWeight(500),
                                    color = White,
                                    letterSpacing = 0.22.sp,
                                )
                            )

                        }

                    }
                }

            }
        }
    }
}

@Composable
private fun getFragments() : List<AdviceArticle> {

    val items = mutableListOf<AdviceArticle>()

    items.add(
        AdviceArticle(
            type = stringResource(R.string.probnik),
            subject = stringResource(R.string.math),
            title = stringResource(R.string.trial_version)
        )
    )

    items.add(
        AdviceArticle(
            type = stringResource(R.string.test),
            subject = stringResource(R.string.math),
            title = "Уравнения высших порядков"
        )
    )

    items.add(
        AdviceArticle(
            type = stringResource(R.string.test),
            subject = stringResource(R.string.math),
            title = "Уравнения высших порядков"
        )
    )

    return items
}

