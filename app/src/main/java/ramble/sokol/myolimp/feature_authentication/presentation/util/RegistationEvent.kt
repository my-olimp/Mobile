package ramble.sokol.myolimp.feature_authentication.presentation.util

sealed class RegistationEvent {

    data object OnSave: RegistationEvent()

    data class onGenderChanged(val gender: String): RegistationEvent()

    data class onNameSurnameChanged(val nameSurname: String): RegistationEvent()

    data class onDobChanded(val dob: String): RegistationEvent()

    data class onActivityTypeChanged(val activityType: String): RegistationEvent()

}