package ramble.sokol.myolimp.feature_profile.navigation_sheets

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object SheetRouter {

    var currentSheet: MutableState<SheetNavigation> = mutableStateOf(SheetNavigation.Empty())

    fun navigateTo(destination : SheetNavigation) {
        currentSheet.value = destination
    }
}
