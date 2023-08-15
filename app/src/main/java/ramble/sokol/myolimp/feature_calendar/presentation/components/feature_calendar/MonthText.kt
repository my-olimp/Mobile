package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import java.time.YearMonth
import java.time.format.TextStyle


/*
* show current month with year
* */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthText(
    selectedMonth: YearMonth
) {
    val context = LocalContext.current

    val monthWithYear = selectedMonth.month.getDisplayName(
        TextStyle.FULL_STANDALONE,
        context.resources.configuration.locales[0]
    ) + " " + selectedMonth.year


    Text(
        text = monthWithYear,
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(500),
            color = Color(0xFF222222),
            letterSpacing = 0.32.sp,
        )
    )
}
