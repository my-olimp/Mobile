package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField
import ramble.sokol.myolimp.feature_library.presenation.components.library.IconWithCircle


@Composable
fun LoveTopBar(
    onTextChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    onCancelSearching: () -> Unit,
    itemCount: Int = 0,
    previousData: String
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
            previousData = previousData,
            onTextChanged = {
                onTextChanged(it)
            }
        ) {
            onCancelSearching()
        }

        Spacer(modifier = Modifier.weight(0.02f))

        IconWithCircle(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f)
                .clip(RoundedCornerShape(8.dp)),
            onClick = onFilterClick,
            isActive = itemCount != 0,
            icon = R.drawable.ic_love_filter,
            itemsCount = itemCount
        )
    }
}

