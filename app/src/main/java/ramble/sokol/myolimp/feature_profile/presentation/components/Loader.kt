package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ramble.sokol.myolimp.ui.theme.MainPageBlue

@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier

            .background(Color.Transparent)
            .clickable {  }
        ,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MainPageBlue
        )
    }
}