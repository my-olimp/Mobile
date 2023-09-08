package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.data.models.SubjectModel
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BorderSubject

@Composable
fun SubjectComponent (
    subject: SubjectModel,
    onClick: (SubjectModel) -> Unit
) {

//    https://developer.android.com/jetpack/compose/components/chip

    Row (
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = BorderSubject,
                shape = RoundedCornerShape(
                    size = 12.dp
                )
            )
            .clip(RoundedCornerShape(
                size = 12.dp
            ))
            .clickable {
                onClick(subject)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Icon(
            painter = painterResource(id = subject.icon),
            tint = BlueStart,
            contentDescription = "icon of ${subject.name}"
        )

        Spacer(modifier = Modifier.width(1.dp))

        Text(
            text = subject.name,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(500),
                color = BlueStart,
                letterSpacing = 0.24.sp,
            )
        )
    }
}
