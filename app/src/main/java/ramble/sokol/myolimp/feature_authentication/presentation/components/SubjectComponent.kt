package ramble.sokol.myolimp.feature_authentication.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BorderSubject
import ramble.sokol.myolimp.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubjectComponent (
    subject: String,
    previouslySelected: Boolean,
    onClick: (String) -> Unit
) {

    var isSelected by remember {
        mutableStateOf(previouslySelected)
    }

//    https://developer.android.com/jetpack/compose/components/chip
    FilterChip(
        onClick = {
            isSelected = !isSelected
            onClick(subject)
        },
        content = {
            Text(
                text = subject,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    letterSpacing = 0.24.sp,
                )
            )
        },
        selected = isSelected,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data("https://storage.yandexcloud.net/myolimp/subject/$subject.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .build()
                ),
                tint = if (isSelected) White else BlueStart,
                contentDescription = "icon of $subject",
            )
        },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isSelected -> BlueStart
                else -> BorderSubject
            }
        ),
        colors = ChipDefaults.filterChipColors(
            backgroundColor = White,
            contentColor = BlueStart,
//            leadingIconColor = BlueStart,

            selectedBackgroundColor = BlueStart,
            selectedContentColor = White,
//            selectedLeadingIconColor = White
        ),
    )
}
