package ramble.sokol.myolimp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.presentation.bottom_navigation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OlimpTheme(
    statusBarColor: Color = Transparent,
    navigationBarColor: Color = Transparent,
    isSplashScreen: Boolean = false,
    isLoading: Boolean = false,
    isError: Boolean = false,
    onReload: () -> Unit,
    content: @Composable () -> Unit,
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    if (isError)  {
        MaterialTheme(
            typography = Typography,
            content = {
                content()

                AlertDialog(
                    onDismissRequest = {},
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false // disable the default size so that we can customize it
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                            .padding(
                                vertical = 36.dp,
                                horizontal = 16.dp
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_connection_error),
                            contentDescription = "connection error",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = stringResource(id = R.string.something_wrong),
                            style = TextStyle(
                                fontSize = 25.sp,
                                lineHeight = 24.5.sp,
                                fontFamily = FontFamily(Font(R.font.bold)),
                                fontWeight = FontWeight(700),
                                color = LightBlack,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(id = R.string.check_your_connection),
                            style = mediumType(
                                fontSize = 14.sp,
                                lineHeight = 13.72.sp,
                                color = GreySecondary,
                                letterSpacing = 0.28.sp
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        FilledBtn(
                            text = stringResource(id = R.string.reload),
                            padding = 0.dp
                        ) { onReload() }
                    }
                }

            }
        )
    } else if (isLoading) {
        MaterialTheme(
            typography = Typography,
            content = {
                content()
                AlertDialog(
                    onDismissRequest = {},
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false // disable the default size so that we can customize it
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 42.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                            .padding(vertical = 36.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = BlueStart
                        )
                    }
                }
            }
        )
    } else if (!isSplashScreen) {

        MaterialTheme(
            typography = Typography,
            content = content
        )

    } else {
        GradientBackground(
            content = content
        )
    }
}

@Composable
fun BottomBarTheme(
    navController: NavController,
    statusBarColor: Color = Transparent,
    navigationBarColor: Color = Transparent,
    isLoading: Boolean = false,
    isError: Boolean = false,
    onReload: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    OlimpTheme (
        navigationBarColor = navigationBarColor,
        statusBarColor = statusBarColor,
        isLoading = isLoading,
        isError = isError,
        onReload = onReload,
        content = {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                },
                content = content,
                backgroundColor = BackgroundMain
            )
        }
    )
}

@Composable
fun CalendarTheme(
    navController: NavController,
    fab: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setNavigationBarColor(
            color = Transparent,
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(
            BackgroundMain,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    MaterialTheme(
        content = {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                },
                content = content,
                backgroundColor = BackgroundMain,
                floatingActionButton = fab
            )
        },
    )

}

@Composable
fun GradientBackground(
    content: @Composable () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setStatusBarColor(
            color = BlueStart,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = BlueEnd,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BlueStart,
                        BlueEnd
                    )
                )
            )
            .paint(
                painterResource(id = R.drawable.splash_screen_backgrond),
                contentScale = ContentScale.FillBounds
            )
    ) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}
