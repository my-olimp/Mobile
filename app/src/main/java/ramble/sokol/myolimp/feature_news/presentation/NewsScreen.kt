package ramble.sokol.myolimp.feature_news.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.destinations.NewsScreenDestination
import ramble.sokol.myolimp.ui.theme.BottomBarTheme

@Destination
@Composable
fun NewsScreen(
    navController: NavController
) {
    BottomBarTheme(
        navController = navController,
        onReload = {
            navController.navigate(NewsScreenDestination)
        }
    ) {
        Text(
            modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = "NewsScreen"
        )
    }
}