package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_clock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(
    time: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    var radiusPx by remember { mutableIntStateOf(0) }

    var radiusInsidePx by remember { mutableIntStateOf(0) }

    val step = Math.PI * 2 / 12

    fun angleForIndex(hour: Int) = -Math.PI / 2 + step * hour

    fun posX(index: Int) =
        ((if (index < 12) radiusPx else radiusInsidePx) * cos(angleForIndex(index))).toInt()

    fun posY(index: Int) =
        ((if (index < 12) radiusPx else radiusInsidePx) * sin(angleForIndex(index))).toInt()

    Box(modifier = modifier) {
        Surface(
            color = Color(0xFFF4F8FF),
            shape = CircleShape,
            modifier = Modifier.fillMaxSize()
        ) {}

        val padding = 4.dp
        val hourCirclePx = 36f

        Layout(
            content = content,
            modifier = Modifier
                .padding(padding)
                .drawBehind {
                    val end = Offset(
                        x = size.width / 2 + posX(time),
                        y = size.height / 2 + posY(time)
                    )

                    drawCircle(
                        radius = 9f,
                        color = Color(0xFF3579F8),
                    )

                    drawLine(
                        start = center,
                        end = end,
                        color = Color(0xFF3579F8),
                        strokeWidth = 4f
                    )

                    drawCircle(
                        radius = hourCirclePx,
                        center = end,
                        color = Color(0xFF3579F8),
                    )
                }
        ) { measurables, constraints ->
                val placeables = measurables.map { it.measure(constraints)
            }

            assert(placeables.count() == 12 || placeables.count() == 24) {
                "Missing items: should be 12 or 24, is ${placeables.count()}"
            }

            layout(constraints.maxWidth, constraints.maxHeight) {
                val size = constraints.maxWidth
                val maxSize = maxOf(placeables.maxOf { it.width }, placeables.maxOf { it.height })

                radiusPx = (constraints.maxWidth - maxSize) / 2
                radiusInsidePx = (radiusPx * 0.67).toInt()

                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        size / 2 - placeable.width / 2 + posX(index),
                        size / 2 - placeable.height / 2 + posY(index),
                    )
                }
            }
        }
    }
}
