package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface RegistrationInfoEvent {

    data object OnCancelLoader : RegistrationInfoEvent

    data object OnLoaderUp: RegistrationInfoEvent

    data class OnNext(val navigator: DestinationsNavigator) : RegistrationInfoEvent

    data class OnGenderChanged(val gender: String): RegistrationInfoEvent

    data class OnNameSurnameChanged(val fio: String): RegistrationInfoEvent

    data class OnDobChanged(val bdate: String): RegistrationInfoEvent

    data class OnActivityTypeChanged(val requestActivityType: String, val activityType: String): RegistrationInfoEvent
}
