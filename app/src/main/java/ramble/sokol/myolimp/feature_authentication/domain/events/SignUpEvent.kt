package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface SignUpEvent {

    data class OnSignUp(val navigator: DestinationsNavigator): SignUpEvent
    data class OnEmailUpdated(val email: String): SignUpEvent
    data class OnPasswordUpdated(val password: String): SignUpEvent
    data class OnConfirmedPasswordUpdated(val confirmedPassword: String): SignUpEvent

}
