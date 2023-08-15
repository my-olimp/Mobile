package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.domain.events.Event
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem

@Composable
fun Searching (
    state: PlanState,
    onEvent: (Event) -> Unit
) {

    val searchingPlans = state.plans.filter {
        it.subject.contains(state.searchQuery, ignoreCase = true) ||
                it.title.contains(state.searchQuery, ignoreCase = true)
    }

    if (searchingPlans.isEmpty()) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "По запросу ${state.searchQuery} ничего не найдено",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                letterSpacing = 0.24.sp,
                textAlign = TextAlign.Center
            )
        )

        Image (
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_calendar_no_plans),
            contentDescription = "no plans",
            alignment = Alignment.Center
        )

    } else {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "По запросу ${state.searchQuery} найден(-о) ${searchingPlans.size} план(-ов)",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                    letterSpacing = 0.24.sp,
                    textAlign = TextAlign.Center
                )
            )

        searchingPlans.forEach {
            PlanItem(
                item = it,
            ) { plan->
                onEvent(Event.DeletePlan(plan))
            }
        }
    }
}