package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_date_picker

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDefaults.YearAbbrMonthDaySkeleton
import androidx.compose.material3.DatePickerDefaults.YearMonthWeekdayDaySkeleton
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSheet(
    onEvent: (Event) -> Unit,
) {

//    val calendar = Calendar.getInstance()
//    val parts = selectedDate.split("-")
//    calendar.set(
//        parts[0].toInt(),
//        parts[1].toInt(),
//        parts[2].toInt()
//    )
//    val currentMillis = calendar.timeInMillis

    val pickerState = rememberDatePickerState(
//        initialSelectedDateMillis = currentMillis,
    )

    // Get today's date and time
    val today = Date()

    // Subtract one day to get yesterday's date
    val cal: Calendar = Calendar.getInstance()
    cal.time = today
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday: Date = cal.time

    // Get yesterday's date in milliseconds
    val yesterdayMillis: Long = yesterday.time

    val formatter = remember {
        DatePickerFormatter(
            yearSelectionSkeleton = YearAbbrMonthDaySkeleton,
            selectedDateSkeleton = YearMonthWeekdayDaySkeleton,
            selectedDateDescriptionSkeleton = YearMonthWeekdayDaySkeleton
        )
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            )
    ) {
        DatePicker (
            state = pickerState,
            dateValidator = {
                it >= yesterdayMillis
            },
            dateFormatter = formatter,
            colors = DatePickerDefaults.colors(
                todayDateBorderColor = BlueStart.copy(alpha = 0.75f),
                todayContentColor = BlackProfile,

                selectedDayContainerColor = BlueStart,
                selectedDayContentColor = White,
            ),
            title = {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 24.dp,
                            end = 12.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.pick_date),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = SheetTitle,
                            textAlign = TextAlign.Start,
                            letterSpacing = 0.4.sp,
                        )
                    )

                    Image (
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .clickable {

                                // send data

                                try {
                                    val simpleDate = SimpleDateFormat("yyyy/MM/dd")
                                    val currentDate = simpleDate
                                        .format(
                                            pickerState.selectedDateMillis
                                        )
                                        .replace("/", "-")

                                    onEvent(Event.OnDateUpdated(currentDate))

                                } catch (_: Exception) {
                                }

                                // close

                                onEvent(Event.OnDatePickerShowing(false))
                            },
                        painter = painterResource(id = R.drawable.ic_profile_cancel),
                        contentDescription = "close sheet"
                    )
                }
            }
        )

    }

}
