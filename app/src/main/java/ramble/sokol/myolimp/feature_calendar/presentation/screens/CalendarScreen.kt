package ramble.sokol.myolimp.feature_calendar.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.destinations.CreateCalendarScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar.ExpandableCalendar
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.CurrentDay
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_favourites.Favourites
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching.Searching
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.CalendarTheme
import ramble.sokol.myolimp.ui.theme.White
import java.time.LocalDate

@Composable
@Destination
fun CalendarScreen(
    navController: NavController,
    viewModel: PlansViewModel = getViewModel()
) {

    val state by viewModel.state.collectAsState()

    viewModel.onEvent(Event.GetPreviousDate)

    Log.i(PlansViewModel.TAG, "get date from view model - ${state.date}")

    CalendarTheme(
        navController = navController,
        fab = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        CreateCalendarScreenDestination(
                            date = state.date
                        )
                    )
                },
                containerColor = BlueStart,
                contentColor = White
            ) {
                Icon (
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "add plan"
                )
            }
        }
    ) {

        val currentDate = remember {
            mutableStateOf(LocalDate.now())
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            item {

                /*
                    Calendar
                */
                ExpandableCalendar(
                    state = state,
                    onDayClick = {
                        currentDate.value = it
                    },
                    onEvent = viewModel::onEvent
                )
            }
            item {

                Column(
                    modifier = Modifier
                        .padding(bottom = 100.dp),
                ) {


                    if (state.isSearching) {

                /*
                    Searching
                */

                    Searching(
                        state = state,
                        navController = navController,
                    )


                    } else if (state.isShowingFavourites) {

                /*
                    Favourites
                */
                        Favourites(
                            state = state,
                            navController = navController,
                        )

                    } else {

                /*
                    Chosen Day
                */
                        CurrentDay(
                            currentDate = currentDate,
                            state = state,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
