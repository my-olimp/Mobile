package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle

/**
 * View that represent one day in the calendar
 * @param date date that view should represent
 * @param weekDayLabel flag that indicates if name of week day should be visible above day value
 * @param modifier view modifier
 */
@Composable
fun DayView(
    date: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    weekDayLabel: Boolean = true,
    state: PlanState
) {

    val isCurrentDay = date == LocalDate.now()

    val dayValueModifier =

        // if current day

        if (isCurrentDay) {
            modifier.background(
                color = BlueStart.copy(alpha = 0.75f),
                shape = CircleShape
            )
        }

        // if this day is selected by user and not showing favourites

        else if (isSelected && !state.isShowingFavourites) {
            modifier.background(
                color = BlueStart,
                shape = CircleShape
            )
        }

        // if it is typically day

        else {
            modifier.background(
                color = White,
                shape = CircleShape
            )
        }

    val toDos = state.plans.filter {
        it.date == date.toString()
    }

    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // show weekDay (Mon, Tue)

        if (weekDayLabel) {
            Text (
                modifier = Modifier
                    .size(25.dp),
                text = DayOfWeek.values()[date.dayOfWeek.value - 1].getDisplayName(
                    TextStyle.SHORT,
                    LocalContext.current.resources.configuration.locales[0]
                ),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(ramble.sokol.myolimp.R.font.regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                    letterSpacing = 0.2.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    onDayClick(date)
                },
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box (
                modifier = Modifier
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                Text (
                    modifier = dayValueModifier
                        .padding(4.dp)
                        .size(24.dp),
                    text = date.dayOfMonth.toString(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(ramble.sokol.myolimp.R.font.regular)),
                        fontWeight = FontWeight(400),
                        color =
                        if ((isSelected && !state.isShowingFavourites) || isCurrentDay) {
                            Color.White
                        } else {
                            BlackProfile
                        },
                        letterSpacing = 0.2.sp,
                        textAlign = TextAlign.Center
                    ),
                )
            }

            Row {
                toDos.forEach {
                    Box (
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .clip(CircleShape)
                            .background(
                                color = Color(it.color.toColorInt()),
                                shape = RoundedCornerShape(size = 100.dp)
                            )
                            .size(8.dp)
                            .animateContentSize()
                    )
                }
            }
        }
    }
}
