package ramble.sokol.myolimp.feature_profile.domain.events

import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryEvent

sealed interface ProfileLoveEvent {

    data object OnEmptyQuery : ProfileLoveEvent

    data object OnFilterSubjects: ProfileLoveEvent

    data class OnChooseCheckbox(val subject: String,val enabled: Boolean): ProfileLoveEvent

    data class OnQueryUpdate(val query: String): ProfileLoveEvent
}
