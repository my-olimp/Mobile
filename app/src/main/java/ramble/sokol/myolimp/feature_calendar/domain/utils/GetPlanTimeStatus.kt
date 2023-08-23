package ramble.sokol.myolimp.feature_calendar.domain.utils

import android.util.Log
import ramble.sokol.myolimp.feature_calendar.domain.view_models.PlansViewModel
import java.util.Calendar

fun getPlanTimeStatus(
    startHour: Int,
    startMin: Int,
    endHour: Int,
    endMin: Int,
) : Int {

    val rightNow = Calendar.getInstance()
    val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY) // 0..23
    val currentMin: Int = rightNow.get(Calendar.MINUTE) // 0..60

    val differenceStartWithCurrent = differenceBetweenTime(
            start = "${addZeroIfNeeded(startHour)}:${addZeroIfNeeded(startMin)}",
            end = "${addZeroIfNeeded(currentHour)}:${addZeroIfNeeded(currentMin)}"
        )

    val differenceEndWithCurrent = differenceBetweenTime(
            start = "${addZeroIfNeeded(endHour)}:${addZeroIfNeeded(endMin)}",
            end = "${addZeroIfNeeded(currentHour)}:${addZeroIfNeeded(currentMin)}"
        )


    Log.i(PlansViewModel.TAG, "differenceStartWithCurrent - ${differenceStartWithCurrent.toMinutes()}")
    Log.i(PlansViewModel.TAG, "differenceEndWithCurrent - ${differenceEndWithCurrent.toMinutes()}")

    if (differenceStartWithCurrent.toMinutes() < 0L && differenceEndWithCurrent.toMinutes() < 0L) {
        // next
        Log.i(PlansViewModel.TAG, "next")

        return PlanTimeStatus.NEXT

    } else if (differenceStartWithCurrent.toMinutes() > 0L && differenceEndWithCurrent.toMinutes() > 0L) {
        // previous
        Log.i(PlansViewModel.TAG, "previous")

        return PlanTimeStatus.PREVIOUS

    } else if ((differenceStartWithCurrent.toMinutes() > 0L && differenceEndWithCurrent.toMinutes() < 0L) || differenceStartWithCurrent.toMinutes() < 0L && differenceEndWithCurrent.toMinutes() > 0L) {
        // current
        Log.i(PlansViewModel.TAG, "current")

        return PlanTimeStatus.CURRENT

    } else {
        // warning

        Log.i(PlansViewModel.TAG, "warning")

        return PlanTimeStatus.ERROR

    }
}

private fun addZeroIfNeeded (
    num: Int
) : String {

    if (num.toString().length == 1) {
        return "0$num"
    }

    return num.toString()
}
