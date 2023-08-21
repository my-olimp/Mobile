package ramble.sokol.myolimp.feature_calendar.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
internal fun Modifier.dayViewModifier(
    date: LocalDate,
    currentMonth: YearMonth? = null,
    monthView: Boolean = false
): Modifier = this.then(
    Modifier.alpha(
        if (
//            (!monthView && date.isBefore(LocalDate.now())) ||
            (!monthView && currentMonth != null && date.isBefore(currentMonth.atDay(1))) ||
            (currentMonth != null && date.isAfter(currentMonth.atEndOfMonth())) ||
            (monthView && currentMonth != null && date.isBefore(currentMonth.atDay(1)))
            ) 0.5f
        else 1f
    )
)
