package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface ForgotPasswordEvent {
    data class OnContinue(val navigator: DestinationsNavigator): ForgotPasswordEvent
    data class OnEmailTyped(val email: String) : ForgotPasswordEvent
}