package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun FavoriteIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(
            color = if (isActive) BlueStart else White,
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Icon(
            painterResource(id = R.drawable.ic_article_favorite),
            contentDescription = "Filter articles",
            tint = if (isActive) White else GreyProfileData
        )
    }
}
