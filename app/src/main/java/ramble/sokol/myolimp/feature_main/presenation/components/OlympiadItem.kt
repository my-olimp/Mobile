package ramble.sokol.myolimp.feature_main.presenation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.feature_main.data.models.OlympiadModel
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.HomeCloseOlympiad
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun OlympiadItem(
    item: OlympiadModel
) {

    val color = if (item.time == "Завтра") HomeCloseOlympiad else BlueStart

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row (
            modifier = Modifier
                .weight(0.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp),
                painter = painterResource(id = item.icon),
                contentDescription = "image of olympiad"
            )
            
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                modifier = Modifier
                    .weight(0.5f),
                text = item.name,
                style = regularType(
                    color = SheetTitle,
                    fontSize = 12.sp,
                )
            )
        }
        
        Spacer(modifier = Modifier.weight(0.1f))

        Box(
            modifier = Modifier
                .weight(0.3f)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = color
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.time,
                style = regularType(
                    color = color,
                    fontSize = 12.sp,
                )
            )
        }
    }
}
