package ramble.sokol.myolimp.feature_main.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import ramble.sokol.myolimp.destinations.CreateCalendarScreenDestination
import ramble.sokol.myolimp.destinations.LibraryScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryBox
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.feature_main.data.models.AdviceArticle
import ramble.sokol.myolimp.feature_main.data.models.OlympiadModel
import ramble.sokol.myolimp.feature_main.presenation.components.CompletedPlanItem
import ramble.sokol.myolimp.feature_main.presenation.components.OlympiadItem
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.MainBackground
import ramble.sokol.myolimp.ui.theme.Transparent
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
    val olympiads= getOlympiads()

    BottomBarTheme(
        navController = navController,
        onReload = {},
        statusBarColor = BackgroundMain,
    ) {

        // Today Plans
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Olympiads
            LibraryBox(
                title = "Ближайшие олимпиады",
                action = "",
                onActionClicked = {
                    // update destination
                    navController.navigate(LibraryScreenDestination) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    items(olympiads) {
                        OlympiadItem(item = it)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        text = stringResource(R.string.don_t_have_plans)
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

            Spacer(modifier = Modifier.height(16.dp))
            
            // 1st step
            LibraryBox(
                title = stringResource(R.string.first_step),
                action = stringResource(R.string.nav_library_name),
                onActionClicked = {
                    // update destination
                    navController.navigate(LibraryScreenDestination) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(fragments) { article ->
                        LibraryItem(
                            subject = article.subject,
                            title = article.title,
                            type = article.type,
                            onClick = {
//                                navController.navigate(ArticleScreenDestination(id = article.id))
                            }
                        )
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

@Composable
private fun getOlympiads() : List<OlympiadModel> {

    val items = mutableListOf<OlympiadModel>()

    items.add(
        OlympiadModel(
            name = "Региональный этап ВСОШ по математике",
            icon = R.drawable.ic_home_olympiad_vshe,
            time = "Завтра"
        )
    )

    items.add(
        OlympiadModel(
            name = "Заключительный этап Высшей Пробы по математике",
            icon = R.drawable.ic_home_olympiad_vshe,
            time = "15 марта"
        )
    )

    items.add(
        OlympiadModel(
            name = "Заключительный этап ВСОШ по математике",
            icon = R.drawable.ic_home_olympiad_vshe,
            time = "18 апреля"
        )
    )

    return items
}

