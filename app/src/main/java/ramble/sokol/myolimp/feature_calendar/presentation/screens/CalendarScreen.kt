package ramble.sokol.myolimp.feature_calendar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import ramble.sokol.myolimp.feature_calendar.presentation.components.CalendarImpl
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.White

@Destination
@Composable
fun CalendarScreen(
    navController: NavController
) {
    BottomBarTheme(
        navController = navController
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.TopCenter
        ) {
            CalendarImpl()
        }

    }
}