package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_library.presenation.components.article.SearchTextField

@Composable
fun LibrarySearchBar(
    itemCount: Int,
    previousData: String,
    onSearchTextChanged: (String) -> Unit,
    onShowFilterBottomSheet: () -> Unit,
    onShowFavourites: (Boolean) -> Unit,
    onCancelSearching: () -> Unit,
    isFilterActive: Boolean = false,
    isFavoriteActive: Boolean = false,
) {
    Row(
        modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Max)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        SearchTextField(
            modifier = Modifier
                .weight(0.68f),
            onTextChanged = onSearchTextChanged,
            onCancelSearching = onCancelSearching,
            previousData = previousData
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        IconWithCircle(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f)
                .clip(RoundedCornerShape(8.dp)),
            onClick = onShowFilterBottomSheet,
            isActive = isFilterActive,
            icon = R.drawable.ic_library_sorting,
            itemsCount = itemCount
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.01f))

        FavoriteIcon(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            onClick = {
                onShowFavourites(!isFavoriteActive)
            },
            isActive = isFavoriteActive
        )
    }
}
