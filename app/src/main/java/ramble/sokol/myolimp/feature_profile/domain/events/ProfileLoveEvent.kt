package ramble.sokol.myolimp.feature_profile.domain.events

sealed interface ProfileLoveEvent {

    data object OnFilterSubjectFromBottomSheet: ProfileLoveEvent

    data class OnCheckboxSubject(val subject: String): ProfileLoveEvent
}
