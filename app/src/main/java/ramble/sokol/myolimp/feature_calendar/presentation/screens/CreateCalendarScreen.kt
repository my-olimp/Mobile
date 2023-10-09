package ramble.sokol.myolimp.feature_calendar.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.CalendarScreenDestination
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock.TimerPicker
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ColorsBox
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ReadOnlyDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.SelectableText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_date_picker.CalendarSheet
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_date_picker.DateInput
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun CreateCalendarScreen (
    navController: NavController,
    date: String,
    fromHome: Boolean = false
) {

    val viewModel = getViewModel<PlansViewModel>()
    val state by viewModel.state.collectAsState()

    val scroll = rememberScrollState()

    val context = LocalContext.current

    val bottomState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            Toast.makeText(context, "Выберите дату и нажмите на крестик )", Toast.LENGTH_SHORT).show()

            false
        },
        skipHalfExpanded = true
    )

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(Event.OnDateUpdated(
            if (!LocalDate.parse(date).isBefore(LocalDate.now())) date else LocalDate.now().toString())
        )
    })

    if (state.isShowingCalendar) {
        LaunchedEffect(key1 = Unit, block = {

            focusManager.clearFocus()

            bottomState.hide()
            bottomState.show()

        })
    }

    OlimpTheme (
        onReload = {

        },
        content = {

            ModalBottomSheetLayout(
                sheetState = bottomState,
                sheetShape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp
                ),
                sheetContent = {

                    if (state.isShowingCalendar) {
    //                     showing date picker

                        CalendarSheet(
                            onEvent = viewModel::onEvent,
    //                        selectedDate = LocalDate.now().toString()
                        )

                    }
                },
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .verticalScroll(scroll)
                        .padding(vertical = 18.dp, horizontal = 32.dp)
                ) {

                    // Top Bar

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = stringResource(R.string.create_plan),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.4.sp,
                            )
                        )

                        Image(
                            modifier = Modifier
                                .clickable {

                                    Log.i(PlansViewModel.TAG, "${state.date} - $date")

                                    if (fromHome) {
                                        // navigate to home if from home

                                        navController.navigate(
                                            HomeScreenDestination()
                                        )
                                    } else {
                                        viewModel.onEvent(
                                            Event.SaveDate(date)
                                        )

                                        navController.navigate(
                                            CalendarScreenDestination()
                                        )
                                    }
                                },
                            painter = painterResource(id = R.drawable.ic_profile_cancel),
                            contentDescription = "close",
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Name

                    OutlinedText(
                        previousData = state.title,
                        label = stringResource(R.string.name_plan),
                        onTextChanged = {
                            viewModel.onEvent(Event.OnTitleUpdated(it))
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Type

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row {
                            SelectableText(
                                text = stringResource(R.string.event),
                                state = state,
                                onTextClick = {
                                    viewModel.onEvent(Event.OnTypeUpdated(it))
                                }
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            SelectableText(
                                text = stringResource(R.string.task),
                                state = state,
                                onTextClick = {
                                    viewModel.onEvent(Event.OnTypeUpdated(it))
                                }
                            )
                        }

                        // Bookmark

                        Box(
                            modifier = Modifier
                                .heightIn(max = 46.dp)
                                .background(
                                    color = if (state.isFavourite) Color(0xFF3579F8) else Color(
                                        0xFFFFFFFF
                                    ),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .clickable {
                                    viewModel.onEvent(Event.OnFavouriteClick(!state.isFavourite))
                                },

                            ) {
                            Icon(
                                modifier = Modifier
                                    .padding(12.dp),
                                painter = painterResource(id = R.drawable.ic_calendar_favourite),
                                contentDescription = "bookmark",
                                tint = if (state.isFavourite) Color(0xFFFFFFFF) else Color(0xFF757575),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Data

                    DateInput(
                        label = stringResource(R.string.data_plan),
                        state = state,
                        onEvent = viewModel::onEvent,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TimerPicker(
                        startHour = state.startHour,
                        startMinute = state.startMinute,
                        endHour = state.endHour,
                        endMinute = state.endMinute,
                        onEvent = viewModel::onEvent
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subject

                    val subjects = listOf(
                        "Астрономия",
                        "Английский",
                        "Литература",
                        "Математика",
                        "Информатика",
                        "Дизайн",
                        "Другое"
                    )

                    ReadOnlyDropDown(
                        options = subjects,
                        label = stringResource(R.string.subject),
                        previousData = state.subject
                    ) {
                        viewModel.onEvent(Event.OnSubjectUpdated(it))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Color

                    val colorsHex = listOf(
                        "#FF7E50FF",
                        "#FFFF985E",
                        "#FF5D96FF",
                        "#FFFF0000",
                        "#FF000000",
                    )

                    ColorsBox(
                        previousData = state.color,
                        colorsHex = colorsHex
                    ) {
                        viewModel.onEvent(Event.OnColorUpdated(it))
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    FilledBtn(
                        text = stringResource(id = R.string.save),
                        padding = 0.dp
                    ) {
                        viewModel.onEvent(Event.CreatePlan(
                            navController = navController
                        ))
                    }
                }
            }
        }
    )
}
