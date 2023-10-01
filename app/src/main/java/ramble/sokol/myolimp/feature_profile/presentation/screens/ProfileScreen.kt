package ramble.sokol.myolimp.feature_profile.presentation.screens

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ArticleScreenDestination
import ramble.sokol.myolimp.destinations.ProfileDataScreenDestination
import ramble.sokol.myolimp.destinations.RegisterImageScreenDestination
import ramble.sokol.myolimp.destinations.RegisterSubjectsScreenDestination
import ramble.sokol.myolimp.feature_profile.presentation.components.Reference
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfile
import ramble.sokol.myolimp.ui.theme.White

@Destination
@Composable
fun ProfileScreen(
    navController: NavController
) {

    val viewModel = getViewModel<ProfileViewModel>()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

//    LaunchedEffect(key1 = Unit, block = {
//        viewModel.onEvent(ProfileEvent.OnRefreshToken)
//    })

    BottomBarTheme(
        navController = navController
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
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

                AsyncImage(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(95.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape),
//                    model = if (state.value.profileImg != null) state.value.profileImg
//                            else R.drawable.ic_default_img,
                    model = "https://storage.yandexcloud.net/myolimp/user/avatar/${state.value.id}.webp",
                    contentDescription = "user logo",
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
                    text = state.value.accountType ?: "Loading",
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
                    content = stringResource(R.string.profile_data_content)
                ) {
                    navController.navigate(
                        ProfileDataScreenDestination()
                    )
                }

                Spacer(
                     modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_purpose,
                    title = stringResource(R.string.profile_purpose),
                    content = stringResource(R.string.profile_purpose_content)
                ) {

                    navController.navigate(RegisterImageScreenDestination)
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Spacer(
                    modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_progress,
                    title = stringResource(R.string.profile_progress),
                    content = stringResource(R.string.profile_progress_content)
                ) {
                    navController.navigate(RegisterSubjectsScreenDestination)
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Spacer(
                    modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_favourite,
                    title = stringResource(R.string.profile_favourite),
                    content = stringResource(R.string.profile_favourite_content)
                ) {
                    navController.navigate(ArticleScreenDestination(1))
                }

                Spacer(
                    modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_watch,
                    title = stringResource(R.string.profile_watch),
                    content = stringResource(R.string.profile_watch_content)
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Spacer(
                    modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_result,
                    title = stringResource(R.string.profile_result),
                    content = stringResource(R.string.profile_result_content)
                ) {
                    Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                }

                Spacer(
                    modifier = Modifier
                        .height(29.dp)
                )

                Reference(
                    icon = R.drawable.ic_profile_logout,
                    title = stringResource(R.string.profile_logout),
                    content = stringResource(R.string.profile_logout_content),
                    isShowBack = false
                ) {
                    viewModel.onEvent(ProfileEvent.OnLogOut(navigator = navController))
                }
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
        navController = navController
    ) {
        Text(
            modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = "NewsScreen"
        )
    }
}
