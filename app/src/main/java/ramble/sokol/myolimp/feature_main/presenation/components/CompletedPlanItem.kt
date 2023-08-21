package ramble.sokol.myolimp.feature_main.presenation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import ramble.sokol.myolimp.ui.theme.BackgroundMain
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.GreyProfileData

@Composable
fun CompletedPlanItem (
    item: PlanModel,
    onComplete: (PlanModel, Boolean) -> Unit
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .background(
                color = BackgroundMain,
                shape = RoundedCornerShape(size = 25.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image (
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable {
                    onComplete(
                        item,
                        !item.isCompleted
                    )
                },
            painter = painterResource(
                if (item.isCompleted) R.drawable.ic_main_completed_plan else  R.drawable.ic_main_plan_not_completed
            ),
            contentDescription = "completed"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = item.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontWeight = FontWeight(400),
                color = if (item.isCompleted) GreyProfileData else BlackProfile,
                textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null,
            )
        )

    }

}
