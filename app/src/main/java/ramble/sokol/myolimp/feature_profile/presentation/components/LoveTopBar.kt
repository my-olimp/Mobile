package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.IconWithCircle
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.CircleColor
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType


@Composable
fun LoveTopBar(
    onTextChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    itemCount: Int = 0
) {

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SearchTextField(
            modifier = Modifier.weight(0.83f),
            onTextChanged = {
                onTextChanged(it)
            }
        ) {
            onTextChanged("")
        }

        Spacer(modifier = Modifier.weight(0.02f))

        IconWithCircle(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f)
                .clip(RoundedCornerShape(8.dp)),
            onClick = onFilterClick,
            isActive = itemCount != 0,
            icon = R.drawable.ic_library_sorting,
            itemsCount = itemCount
        )
    }
}

