package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface RegistrationEvent {

    data class OnNext(val navigator: DestinationsNavigator) : RegistrationEvent

    data class OnGenderChanged(val gender: String): RegistrationEvent

    data class OnNameSurnameChanged(val fio: String): RegistrationEvent

    data class OnDobChanged(val bdate: String): RegistrationEvent

    data class OnActivityTypeChanged(val activityType: String): RegistrationEvent

    data class OnSchoolChanged(val school: String) : RegistrationEvent

    data class OnRegionChanged(val region: String) : RegistrationEvent

    data class OnCityChanged(val city: String) : RegistrationEvent

    data class OnGradeChanged(val grade: String) : RegistrationEvent

}