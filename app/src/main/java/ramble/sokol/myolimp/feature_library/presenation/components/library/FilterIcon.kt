package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.CircleColor
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun FilterIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, end = 7.dp)
                .background(
                    color = if (isActive) BlueStart else White,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Icon(
                painterResource(id = R.drawable.ic_library_sorting),
                contentDescription = "Filter articles",
                tint = if (isActive) White else GreyProfileData
            )
        }

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "1",
                    style = regularType(
                        color = White,
                        fontSize = 11.sp,
                        letterSpacing = 0.22.sp
                    ),
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = White,
                                radius = this.size.maxDimension / 1.7f
                            )
                            drawCircle(
                                color = CircleColor,
                                radius = this.size.maxDimension / 2.0f
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun FilterIcon2 (
    modifier: Modifier
) {

    // TODO get icon / return onClick / update search

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
//                onFilterClick()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = BlueStart,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 4.dp),
                painter = painterResource(id = R.drawable.ic_love_filter),
                contentDescription = "open filter love",
                tint = White
            )
        }
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "2",
                    style = regularType(
                        color = White,
                        fontSize = 11.sp,
                        letterSpacing = 0.22.sp
                    ),
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = White,
                                radius = this.size.maxDimension / 1.7f
                            )
                            drawCircle(
                                color = CircleColor,
                                radius = this.size.maxDimension / 2.0f
                            )
                        }
                )
            }
        }
    }

}
