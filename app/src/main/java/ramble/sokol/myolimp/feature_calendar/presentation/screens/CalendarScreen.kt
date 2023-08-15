package ramble.sokol.myolimp.feature_calendar.presentation.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar.Calendar
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.CreatePlan
import ramble.sokol.myolimp.ui.theme.BottomBarTheme

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun CalendarScreen(
    navController: NavController
) {
//    BottomBarTheme(
//        navController = navController
//    ) {

        val viewModel = getViewModel<PlansViewModel>()
        val state by viewModel.state.collectAsState()


        val bottomState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {

                // to follow changes and send them in view model

                if (it == ModalBottomSheetValue.Hidden) {
                    viewModel.onEvent(Event.HideCreatingSheet)
                } else {
                    viewModel.onEvent(Event.ShowCreatingSheet)
                }

                true
            },
            skipHalfExpanded = false
        )

        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = bottomState,
            sheetShape = RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp
            ),
            sheetContent = {

                // Create Plan Sheet
                CreatePlan(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            },
        ) {

            // to open creating sheet
            if (state.isAddingPlan) {
                LaunchedEffect(key1 = true, block = {
                    coroutineScope.launch {
                        bottomState.hide()
                        bottomState.show()
                    }
                })
            } else {
                LaunchedEffect(key1 = true, block = {
                    coroutineScope.launch {
                        bottomState.hide()
                    }
                })
            }

            // Screen with calendar
            Calendar (
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent,
            )
        }
//    }
}
