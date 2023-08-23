package ramble.sokol.myolimp.feature_calendar.domain.utils

import java.time.Duration
import java.time.LocalTime

fun differenceBetweenTime (
    start: String,
    end: String
): Duration {

    val startInclusive = LocalTime.parse(start)
    val endInclusive = LocalTime.parse(end)

    return Duration.between(startInclusive, endInclusive)
}
