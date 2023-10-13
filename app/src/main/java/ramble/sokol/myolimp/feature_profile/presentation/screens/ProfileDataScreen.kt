package ramble.sokol.myolimp.feature_profile.presentation.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.ProfileDataScreenDestination
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter
import ramble.sokol.myolimp.feature_profile.presentation.components.BottomSheetLayout
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileFilledBtn
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileOutlinedBtn
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileSectionContent
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileSectionTitle
import ramble.sokol.myolimp.feature_profile.presentation.sheets.EditContactsSheet
import ramble.sokol.myolimp.feature_profile.presentation.sheets.EditEducationSheet
import ramble.sokol.myolimp.feature_profile.presentation.sheets.EditPersonalInfoSheet
import ramble.sokol.myolimp.feature_profile.presentation.sheets.EditPhotoSheet
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfileAchievement
import ramble.sokol.myolimp.ui.theme.White


@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun ProfileDataScreen(
    navController: NavController,
) {

    val viewModel = getViewModel<ProfileViewModel>()
    val state = viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            false
        }
    )
    var isCenter by remember {
        mutableStateOf(false)
    }
    var sheetName by remember {
        mutableStateOf("")
    }

    val imgState = viewModel.imgState.collectAsState()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current.applicationContext)
            .data("https://storage.yandexcloud.net/myolimp/user/avatar/${state.value.id}.webp")
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    )

    LaunchedEffect(key1 = imgState.value.isImgChanged, block = {
        painter.onForgotten()
        painter.onRemembered()

        viewModel.onEvent(ProfileEvent.OnImgUpdated)
    })

    BottomBarTheme(
        navController = navController,
        isLoading = !state.value.isLoaded,
        onReload = {
            navController.navigate(ProfileDataScreenDestination)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(if (!state.value.isLoaded) 4.dp else 0.dp),
            contentAlignment = Center
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 52.dp),
                horizontalAlignment = CenterHorizontally,
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
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 40.dp,
                                bottomStart = 40.dp
                            )
                        ),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(95.dp)
                            .align(CenterHorizontally)
                            .clip(CircleShape),
                        painter = painter,
                        contentDescription = "custom transition based on painter state",
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileFilledBtn(text = stringResource(R.string.edit)) {
                            SheetRouter.navigateTo(SheetNavigation.EditPhoto)
                        }

                        ProfileOutlinedBtn(text = stringResource(R.string.delete)) {
                            viewModel.onEvent(ProfileEvent.OnDeleteImg)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

            /*
                Achievements
            */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(size = 25.dp)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        )
                ) {

                    ProfileSectionTitle(
                        text = stringResource(R.string.achivement),
                        isShowEdit = false
                    ) {}

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterHorizontally),
                        painter = painterResource(id = R.drawable.ic_profile_no_achivement),
                        contentDescription = "image description"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.with_out_achivement),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            fontWeight = FontWeight(400),
                            color = GreyProfileAchievement,
                            letterSpacing = 0.3.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                /*
                Personal Data
            */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(size = 25.dp)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        )
                ) {

                    ProfileSectionTitle(
                        text = stringResource(R.string.personal_data)
                    ) {
                        SheetRouter.navigateTo(SheetNavigation.EditPersonalData)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.id),
                        content = state.value.id ?: "Loading"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.name_surname),
                        content = "${state.value.firstName} ${state.value.secondName} ${state.value.thirdName}"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.dob),
                        content = state.value.dateOfBirth ?: "Loading"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.gender),
                        content = if (state.value.gender == "m") stringResource(R.string.man_gender) else stringResource(
                            R.string.women_gender
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.snils),
                        content = state.value.snils ?: "Loading"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                /*
                Education
            */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(size = 25.dp)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        )
                ) {

                    ProfileSectionTitle(
                        text = stringResource(R.string.education)
                    ) {
                        SheetRouter.navigateTo(SheetNavigation.EditEducation)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.region),
                        content = state.value.region?.name ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.city),
                        content = state.value.city?.name ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.school),
                        content = state.value.school?.name ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.grade),
                        content = "${state.value.grade}"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            /*
            Contacts
            */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(size = 25.dp)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        )
                ) {
                    ProfileSectionTitle(
                        text = stringResource(R.string.contacts)
                    ) {
//                        TODO wait for backend
                        SheetRouter.navigateTo(SheetNavigation.EditContacts)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.email),
                        content = state.value.email ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileSectionContent(
                        title = stringResource(R.string.phone_number),
                        content = state.value.phone ?: ""
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

    BottomSheetLayout (
        sheetState = sheetState,
        isCenter = isCenter,
        name = sheetName,

    ) {
        Crossfade(targetState = SheetRouter.currentSheet, label = "") {
            when (it.value) {

                is SheetNavigation.EditPhoto -> {
                    EditPhotoSheet(viewModel = viewModel)

                    isCenter = true
                    sheetName = stringResource(R.string.profile_image)

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditPersonalData -> {
                    EditPersonalInfoSheet(viewModel = viewModel)

                    isCenter = false
                    sheetName = stringResource(R.string.personal_info)

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditEducation -> {
                    EditEducationSheet(viewModel = viewModel)

                    isCenter = false
                    sheetName = stringResource(R.string.education)

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditContacts -> {
                    EditContactsSheet(viewModel = viewModel)

                    isCenter = false
                    sheetName = stringResource(R.string.contacts)

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.Empty -> {
                    (it.value as SheetNavigation.Empty).onDetach.invoke()
                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    })
                }
            }
        }
    }
}
