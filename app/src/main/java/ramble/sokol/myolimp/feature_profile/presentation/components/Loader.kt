package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ramble.sokol.myolimp.ui.theme.LoaderBackgroundColor
import ramble.sokol.myolimp.ui.theme.MainPageBlue

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoaderBackgroundColor)
            .clickable {  }
        ,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MainPageBlue
        )
    }
}