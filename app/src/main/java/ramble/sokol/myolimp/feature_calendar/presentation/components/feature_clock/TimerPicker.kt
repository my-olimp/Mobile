package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.ClockMarks24h
import ramble.sokol.myolimp.feature_calendar.domain.utils.TimePart

@Composable
fun TimerPicker(
    state: PlanState,
    onEvent: (Event) -> Unit
) {
    var selectedPart by remember {
        mutableStateOf(TimePart.StartHour)
    }

    var selectedHour by remember {
        mutableIntStateOf(state.startHour)
    }
    var selectedMinute by remember {
        mutableIntStateOf(state.startMinute)
    }
    var selectedEndHour by remember {
        mutableIntStateOf(state.endHour)
    }
    var selectedEndMinute by remember {
        mutableIntStateOf(state.endMinute)
    }

    val selectedTime by remember {
        derivedStateOf {
            when (selectedPart) {
                TimePart.StartHour -> selectedHour
                TimePart.StartMinute -> selectedMinute / 5
                TimePart.EndHour -> selectedEndHour
                TimePart.EndMinute -> selectedEndMinute / 5
            }
        }
    }

    val onTime: (Int) -> Unit = remember {
        {
            when (selectedPart) {
                TimePart.StartHour -> {
                    selectedHour = it
                    onEvent(Event.OnStartHourUpdated(selectedHour))

                    // next part
                    selectedPart = TimePart.StartMinute
                }
                TimePart.StartMinute -> {
                    selectedMinute = it * 5
                    onEvent(Event.OnStartMinUpdated(selectedMinute))

                    // next part
                    selectedPart = TimePart.EndHour
                }
                TimePart.EndHour -> {
                    selectedEndHour = it
                    onEvent(Event.OnEndHourUpdated(selectedEndHour))

                    // next part
                    selectedPart = TimePart.EndMinute
                }
                TimePart.EndMinute -> {
                    selectedEndMinute = it * 5
                    onEvent(Event.OnEndMinUpdated(selectedEndMinute))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFF757575),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(all = 16.dp)
    ) {
        Text (
            text = "Выберите время начала и конца",

            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = Color(0xE5222222),
                letterSpacing = 0.28.sp,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeCard(
                modifier = Modifier
                    .weight(0.25f),
                time = selectedHour,
                isSelected = selectedPart == TimePart.StartHour,
                onClick = { selectedPart = TimePart.StartHour }
            )

            Text(
                text = ":",
                fontSize = 26.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(horizontal = 2.dp)
            )

            TimeCard(
                modifier = Modifier
                    .weight(0.25f),
                time = selectedMinute,
                isSelected = selectedPart == TimePart.StartMinute,
                onClick = {
                    selectedPart = TimePart.StartMinute
                }
            )

            Text(
                text = "-",
                fontSize = 26.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(horizontal = 2.dp)
            )

            TimeCard(
                modifier = Modifier
                    .weight(0.25f),
                time = selectedEndHour,
                isSelected = selectedPart == TimePart.EndHour,
                onClick = { selectedPart = TimePart.EndHour }
            )

            Text(
                text = ":",
                fontSize = 26.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(horizontal = 2.dp)
            )

            TimeCard(
                modifier = Modifier
                    .weight(0.25f),
                time = selectedEndMinute,
                isSelected = selectedPart == TimePart.EndMinute,
                onClick = {
                    selectedPart = TimePart.EndMinute
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Clock(
            time = selectedTime,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            ClockMarks24h(selectedPart, selectedTime, onTime)
        }
    }
}
