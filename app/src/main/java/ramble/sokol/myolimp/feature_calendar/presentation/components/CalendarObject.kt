package ramble.sokol.myolimp.feature_calendar.presentation.components

import android.graphics.Paint
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_calendar.models.CalendarInput
import ramble.sokol.myolimp.feature_calendar.utils.Constants.CALENDAR_COLUMNS
import ramble.sokol.myolimp.feature_calendar.utils.Constants.CALENDAR_ROWS
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart

@Composable
fun CalendarObject(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarInput>,
    onDateClicked: (Int) -> Unit,
    strokeWidth: Float = 15f,
    month: String
) {

    var calendarSize by remember {
        mutableStateOf(Size.Zero)
    }

    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var animationRadius by remember {
        mutableFloatStateOf(0f)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = month,
            color = BlackProfile,
            fontSize = 40.sp,
        )

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures(onTap = { offset ->
                    val column = (offset.x / calendarSize.width * CALENDAR_COLUMNS).toInt() + 1
                    val row = (offset.y / calendarSize.height * CALENDAR_ROWS).toInt() + 1
                    val day = column + (row - 1) * CALENDAR_COLUMNS

                    if (day <= calendarInput.size) {
                        onDateClicked(day)
                        clickAnimationOffset = offset
                        scope.launch {
                            animate(
                                initialValue = 0f,
                                targetValue = 300f,
                                animationSpec = tween(
                                    durationMillis = 300
                                )
                            ) { value, _ ->
                                animationRadius = value
                            }
                        }
                    }
                })
            }) {

            val canvasHeight = size.height
            val canvasWidth = size.width
            val ySteps = canvasHeight / CALENDAR_ROWS
            val xSteps = canvasWidth / CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / calendarSize.width * CALENDAR_COLUMNS).toInt() + 1
            val row = (clickAnimationOffset.y / calendarSize.height * CALENDAR_ROWS).toInt() + 1

            val path = Path().apply {

                moveTo((column - 1) * xSteps, (row - 1) * ySteps)

                lineTo(column * xSteps, (row - 1) * ySteps)

                lineTo(column * xSteps, row * ySteps)

                lineTo((column - 1) * xSteps, row * ySteps)

                close()

            }

            clipPath(
                path = path
            ) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(BlueStart.copy(0.8f), BlueStart.copy(0.2f)),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    )
                )
            }

            calendarSize = Size(canvasWidth, canvasHeight)

            // draw border
            drawRoundRect(
                BlueStart, cornerRadius = CornerRadius(25f, 25f), style = Stroke(
                    width = strokeWidth
                )
            )

            // draw seperated line in rows
            for (i in 1 until CALENDAR_ROWS) {
                drawLine(
                    color = BlueStart,
                    start = Offset(0f, ySteps * i),
                    end = Offset(canvasWidth, ySteps * i),
                    strokeWidth = strokeWidth
                )
            }

            // draw seperated line in columns
            for (i in 1 until CALENDAR_COLUMNS) {
                drawLine(
                    color = BlueStart,
                    start = Offset(xSteps * i, 0f),
                    end = Offset(xSteps * i, canvasHeight),
                    strokeWidth = strokeWidth
                )
            }

            val textHeight = 17.dp.toPx()

            for (i in calendarInput.indices) {
                val textPositionX = xSteps * (i % CALENDAR_COLUMNS) + strokeWidth
                val textPositionY = (i / CALENDAR_COLUMNS) * ySteps + textHeight + strokeWidth / 2

                drawContext.canvas.nativeCanvas.apply {
                    drawText("${i + 1}", textPositionX, textPositionY, Paint().apply {
                        textSize = textHeight
                        color = BlackProfile.toArgb()
                        isFakeBoldText = true
                    })
                }

            }
        }
    }

}
