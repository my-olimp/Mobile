package ramble.sokol.myolimp.feature_profile.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.EditorScreenDestination
import ramble.sokol.myolimp.destinations.NewsScreenDestination
import ramble.sokol.myolimp.destinations.ProfileDataScreenDestination
import ramble.sokol.myolimp.destinations.ProfileLoveScreenDestination
import ramble.sokol.myolimp.destinations.ProfileScreenDestination
import ramble.sokol.myolimp.destinations.RegisterInfoScreenDestination
import ramble.sokol.myolimp.feature_profile.presentation.components.Reference
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyNavigationText
import ramble.sokol.myolimp.ui.theme.GreyProfile
import ramble.sokol.myolimp.ui.theme.White

@Destination
@Composable
fun ProfileScreen(
    navController: NavController
) {

    val viewModel = getViewModel<ProfileViewModel>()
    val state = viewModel.state.collectAsState()
    val imgState = viewModel.imgState.collectAsState()
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current.applicationContext)
            .data("https://storage.yandexcloud.net/myolimp/user/avatar/${state.value.id}.webp")
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    )

    val version = "v.0.6.9"

    LaunchedEffect(key1 = imgState.value.isImgChanged) {
        painter.onForgotten()
        painter.onRemembered()

        viewModel.onEvent(ProfileEvent.OnImgUpdated)
    }

    BottomBarTheme(
        navController = navController,
        isLoading = !state.value.isLoaded,
        onReload = {
            navController.navigate(ProfileScreenDestination)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .blur(if (!state.value.isLoaded) 4.dp else 0.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        /*
            Top Bar
        */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = White, shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 0.dp, bottomEnd = 40.dp, bottomStart = 40.dp
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(95.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape),
                    painter = painter,
                    contentDescription = "custom transition based on painter state",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${state.value.firstName} ${state.value.secondName}", style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        fontWeight = FontWeight(600),
                        color = BlackProfile,
                        letterSpacing = 0.4.sp,
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = when (state.value.accountType) {
                        "t" -> {
                            stringResource(R.string.teacher)
                        }
                        else -> {
                            stringResource(R.string.student)
                        }
                    },
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = GreyProfile,
                        letterSpacing = 0.26.sp,
                    )
                )
            }

        /*
           References
        */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(size = 25.dp)
                    )
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Reference(
                    icon = R.drawable.ic_profile_data,
                    title = stringResource(R.string.profile_data),
                    content = stringResource(R.string.profile_data_content),
                    isVisible = true
                ) {
                    navController.navigate(
                        ProfileDataScreenDestination()
                    )
                }

                Reference(
                    icon = R.drawable.ic_profile_purpose,
                    title = stringResource(R.string.profile_purpose),
                    content = stringResource(R.string.profile_purpose_content),
                    isVisible = state.value.accountType != "t"
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Reference(
                    icon = R.drawable.ic_profile_progress,
                    title = stringResource(R.string.profile_progress),
                    content = stringResource(R.string.profile_progress_content),
                    isVisible = state.value.accountType != "t"
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Reference(
                    icon = R.drawable.ic_profile_olympiads,
                    title = stringResource(id = R.string.profile_section_olympiads),
                    content = stringResource(id = R.string.profile_section_olympiads_content),
                    isVisible = state.value.accountType == "t"
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }


                Reference(
                    icon = R.drawable.ic_profile_favourite,
                    title = stringResource(R.string.profile_favourite),
                    content = stringResource(R.string.profile_favourite_content),
                    isVisible = true
                ) {
                    navController.navigate(ProfileLoveScreenDestination)
                }

                Reference(
                    icon = R.drawable.ic_profile_watch,
                    title = stringResource(R.string.profile_watch),
                    content = stringResource(R.string.profile_watch_content),
                    isVisible = true
                ) {
                    navController.navigate(RegisterInfoScreenDestination)
                }


                Reference(
                    icon = R.drawable.ic_profile_news,
                    title = stringResource(id = R.string.profile_section_news),
                    content = stringResource(id = R.string.profile_section_news_content),
                    isVisible = state.value.accountType == "t"
                ) {
                    navController.navigate(RegisterInfoScreenDestination)
                }

                Reference(
                    icon = R.drawable.ic_profile_result,
                    title = stringResource(R.string.profile_result),
                    content = stringResource(R.string.profile_result_content),
                    isVisible = state.value.accountType != "t"
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Reference(
                    icon = R.drawable.ic_profile_logout,
                    title = stringResource(R.string.profile_logout),
                    content = stringResource(R.string.profile_logout_content),
                    isShowBack = false
                ) {
                    viewModel.onEvent(ProfileEvent.OnLogOut(navigator = navController))
                }

                Spacer(modifier = Modifier.height(16.dp))

                FilledBtn(
                    text = "Open Editor [BETA]"
                ) {
                    navController.navigate(EditorScreenDestination)
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                FilledBtn(
                    text = if(state.value.accountType == "t") "change into pupil" else "change into teacher"
                ) {
                    viewModel.onEvent(ProfileEvent.OnChangeType(if(state.value.accountType == "t") "p" else "t"))
                }

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
