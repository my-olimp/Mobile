package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface SendCodeEvent {
    data class OnContinue(val navigator: DestinationsNavigator): SendCodeEvent
    data class OnEmailTyped(val email: String) : SendCodeEvent

    data class OnResendCode(val navigator: DestinationsNavigator,val email: String, val isRegistering: Boolean): SendCodeEvent
}