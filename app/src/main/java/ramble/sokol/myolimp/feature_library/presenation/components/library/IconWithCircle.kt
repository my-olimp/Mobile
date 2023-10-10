package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.CircleColor
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun IconWithCircle(
    modifier: Modifier = Modifier,
    icon: Int,
    itemsCount: Int,
    isActive: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .background(
                    color = if (isActive) BlueStart else White,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Icon(
                painterResource(id = icon),
                contentDescription = "Filter articles",
                tint = if (isActive) White else GreyProfileData
            )
        }

        if (itemsCount != 0) {
            Box(
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
                        text = itemsCount.toString(),
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
}
