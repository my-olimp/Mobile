package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun PlanItem  (
    item: PlanModel,
    onEdit: (PlanModel) -> Unit
){

    val times = getCorrectTime(
        item = item
    )

    val date = getCorrectDate(
        date = item.date
    )

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = Color(item.color.toColorInt()),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column (
            modifier = Modifier
                .weight(0.9f)
        ) {

            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = White,
                    letterSpacing = 0.24.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${times[0]} - ${times[1]}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(600),
                    color = White,
                    letterSpacing = 0.24.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = date,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontWeight = FontWeight(500),
                    color = White,
                    letterSpacing = 0.24.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.subject,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontWeight = FontWeight(500),
                    color = White,
                    letterSpacing = 0.24.sp,
                )
            )
        }
        Column (
            modifier = Modifier
                .weight(0.1f)
        ) {
            IconButton(
                onClick = {
                    onEdit(item)
                },
            ) {
                Icon (
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit plan",
                    tint = Color.White,
                )
            }
        }
    }
}

private fun getCorrectTime(
    item: PlanModel
) : List<String> {

    val times = listOf(
        item.startHour,
        item.startMinute,
        item.endHour,
        item.endMinute,
    )

    val resultList = mutableListOf<String>()

    for (i in times.indices) {

        if (times[i].toString().length == 1 && times[i] < 10) {

            resultList.add("0${times[i]}")

        } else {

            resultList.add(times[i].toString())

        }

    }

    return listOf(
        "${resultList[0]}:${resultList[1]}",
        "${resultList[2]}:${resultList[3]}",
    )

}

private fun getCorrectDate(
    date: String
) : String {

    val items = date.split("-")

    return "${items[2]}.${items[1]}.${items[0]}"

}
