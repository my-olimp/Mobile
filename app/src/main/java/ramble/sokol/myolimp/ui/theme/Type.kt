package ramble.sokol.myolimp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

fun regularType(
    color: Color = BlackRegistrationData,
    fontSize: TextUnit = 14.sp,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = TextUnit.Unspecified
) = TextStyle(
        fontSize = fontSize,
        fontFamily = FontFamily(Font(R.font.regular)),
        fontWeight = FontWeight(400),
        color = color,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight
)

fun mediumType(
    color: Color = BlackProfile,
    fontSize: TextUnit = 18.sp,
    letterSpacing: TextUnit = 0.4.sp,
    lineHeight: TextUnit = TextUnit.Unspecified
) = TextStyle(
    fontSize = fontSize,
    fontFamily = FontFamily(Font(R.font.medium)),
    fontWeight = FontWeight(500),
    color = color,
    letterSpacing = letterSpacing,
    lineHeight = lineHeight
)
