package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.screens

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.destinations.OnBoardingScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserViewModel
import ramble.sokol.myolimp.ui.theme.GreyNavigationText
import ramble.sokol.myolimp.ui.theme.OlimpTheme

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    OlimpTheme (
        isSplashScreen = true
    ) {

        val splashViewModel = getViewModel<LocalUserViewModel>()
        val user = splashViewModel.user.collectAsState()
        val isError = splashViewModel.isError.value

        val version = "v.0.4.2"

        val transition = rememberInfiniteTransition(label = "")
        val alpha by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 500
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        val repository = CodeDataStore()

        LaunchedEffect(
            key1 = Unit
        ) {
            delay(2000L)

            try {

                splashViewModel.getUser()

                repository.getToken(ACCESS_TOKEN).first() ?: throw Exception("no access")

                // after that user won't be able to go to previous page
                navigator.popBackStack()

                if (!isError) {
                    /*
                    After delay launch home screen
                */

//                Log.i(TAG, "us - ${user.value.first()}")

                    navigator.navigate(HomeScreenDestination)
                } else {
                    /*
                    After delay launch onBoarding
                */

                    // if user is not registered
                    navigator.navigate(OnBoardingScreenDestination)
                }

            } catch (ex: Exception) {
                navigator.navigate(OnBoardingScreenDestination)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .weight(0.5f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = alpha),
                    painter = painterResource(R.drawable.splash_screen_name),
                    contentDescription = "app image name"
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = version,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(300),
                        color = GreyNavigationText,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp,
                    ),
                )
            }
        }
    }
}
