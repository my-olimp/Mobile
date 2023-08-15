package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.CurrentDay
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites.Favourites
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching.Searching
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.White
import java.time.LocalDate

@Composable
fun Calendar(
    navController: NavController,
    state: PlanState,
    onEvent: (Event) -> Unit,
) {
    BottomBarTheme(
        navController = navController
    ) {
        val currentDate = remember {
            mutableStateOf(LocalDate.now())
        }

        Scaffold (
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onEvent(Event.ShowCreatingSheet)
                    },
                    containerColor = BlueStart,
                    contentColor = White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add plan"
                    )
                }
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundMain),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                item {

                    /*
                        Calendar
                    */
                    ExpandableCalendar (
                        state = state,
                        onDayClick = {
                            currentDate.value = it
                        },
                        onEvent = onEvent
                    )
                }

                if (state.isSearching) {

                    /*
                        Searching
                    */

                    item {
                        Searching(
                            state = state,
                            onEvent = onEvent
                        )
                    }

                } else if (state.isShowingFavourites) {

                    /*
                        Favourites
                    */

                    item {
                        Favourites(
                            state = state,
                            onEvent = onEvent
                        )
                    }

                } else  {

                    /*
                        Chosen Day
                    */

                    item {
                        CurrentDay(
                            currentDate = currentDate,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }
    }
}
