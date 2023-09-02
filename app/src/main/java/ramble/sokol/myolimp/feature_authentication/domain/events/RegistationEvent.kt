package ramble.sokol.myolimp.feature_authentication.domain.events

sealed interface RegistrationEvent {

    data object OnSave: RegistrationEvent

    data class OnGenderChanged(val gender: String): RegistrationEvent

    data class OnNameSurnameChanged(val fio: String): RegistrationEvent

    data class OnDobChanged(val bdate: String): RegistrationEvent

    data class OnActivityTypeChanged(val activityType: String): RegistrationEvent

}