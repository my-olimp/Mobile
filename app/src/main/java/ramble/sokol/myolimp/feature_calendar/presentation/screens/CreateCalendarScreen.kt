package ramble.sokol.myolimp.feature_calendar.presentation.screens

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
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock.TimerPicker
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ColorsBox
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ListDropDown
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.SelectableText
import ramble.sokol.myolimp.feature_profile.presentation.components.CalendarInput
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.OlimpTheme

@Destination
@Composable
fun CreateCalendarScreen (
    date: String,
    navController: NavController
) {

    val viewModel = getViewModel<PlansViewModel>()
    val state by viewModel.state.collectAsState()

    val scroll = rememberScrollState()

    viewModel.onEvent(Event.OnDateUpdated(date = date))

    OlimpTheme {
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
                    painter = painterResource(id = R.drawable.ic_profile_cancel),
                    contentDescription = "close",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                CalendarScreenDestination()
                            )
                        }
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
                            color = if (state.isFavourite) Color(0xFF3579F8) else Color(0xFFFFFFFF),
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
            CalendarInput(
                previousData = date,
                label = stringResource(R.string.data_plan),
                onTextChanged = {
                    viewModel.onEvent(Event.OnDateUpdated(it))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TimerPicker(
                state = state,
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

            ListDropDown(
                subjects = subjects
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
                state = state,
                colorsHex = colorsHex
            ) {
                viewModel.onEvent(Event.OnColorUpdated(it))
            }

            Spacer(modifier = Modifier.height(32.dp))

            FilledBtn(
                text = stringResource(id = R.string.save),
                padding = 0.dp
            ) {
                viewModel.onEvent(Event.CreatePlan(navController = navController))
            }
        }
    }
}
