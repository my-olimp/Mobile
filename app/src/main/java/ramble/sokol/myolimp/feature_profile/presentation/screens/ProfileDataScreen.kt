package ramble.sokol.myolimp.feature_profile.presentation.screens

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.R
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
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfileAchivement
import ramble.sokol.myolimp.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun ProfileDataScreen(
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { false }
    )

    BottomBarTheme(
        navController = navController
    ) {

        val context = LocalContext.current

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
                            topStart = 0.dp, topEnd = 0.dp, bottomEnd = 40.dp, bottomStart = 40.dp
                        )
                    ),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.padding(top = 8.dp),
                    painter = painterResource(id = R.drawable.ic_default_img),
                    contentDescription = "user image"
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileFilledBtn(text = stringResource(R.string.edit)) {
                        Toast.makeText(context, "Edit Photo", Toast.LENGTH_SHORT).show()
                        SheetRouter.navigateTo(SheetNavigation.EditPhoto)
                    }

                    ProfileOutlinedBtn(text = stringResource(R.string.delete)) {
                        Toast.makeText(context, "It's developing", Toast.LENGTH_SHORT).show()
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            /*
                Achievements
            */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
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
                        color = GreyProfileAchivement,
                        letterSpacing = 0.3.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

            }

            /*
                Personal Data
            */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
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
                    Toast.makeText(context, "Edit Personal Data", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.id),
                    content = "123456"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.name_surname),
                    content = "Спиридонова Диана Романовна"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.gender),
                    content = "Женский"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.gender),
                    content = "Женский"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.snils),
                    content = "123-456-789-99"
                )
            }

            /*
                Education
            */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
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
                    Toast.makeText(context, "Edit Education", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.region),
                    content = "Московская область"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.city),
                    content = "Балашиха"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.school),
                    content = "МБОУ СШ № 1"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.grade),
                    content = "10"
                )
            }

            /*
                Contacts
            */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
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
                    SheetRouter.navigateTo(SheetNavigation.EditEducation)
                    Toast.makeText(context, "Edit Contacts", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.email),
                    content = "polyubvi@ya.ru"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileSectionContent(
                    title = stringResource(R.string.phone_number),
                    content = "+7 123 456 78 90"
                )
            }
        }
    }

    BottomSheetLayout(
        sheetState = sheetState
    ) {
        Crossfade(targetState = SheetRouter.currentSheet, label = "") {
            when (it.value) {

                is SheetNavigation.EditPhoto -> {
                    EditPhotoSheet()

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditPersonalData -> {
                    EditPersonalInfoSheet()

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditEducation -> {
                    EditEducationSheet()

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.EditContacts -> {
                    EditContactsSheet()

                    LaunchedEffect(key1 = true, block = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                is SheetNavigation.Empty -> {
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
