package ramble.sokol.myolimp.feature_profile.navigation_sheets

sealed class SheetNavigation {
    data object EditPhoto : SheetNavigation()
    data object EditPersonalData : SheetNavigation()
    data object EditEducation : SheetNavigation()
    data object EditContacts : SheetNavigation()
    data class Empty(val onDetach: () -> Unit = {}) : SheetNavigation()
}
