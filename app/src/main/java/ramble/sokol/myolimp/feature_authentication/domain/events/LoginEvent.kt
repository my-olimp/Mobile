package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface LoginEvent {

    data class OnLogin(val navigator: DestinationsNavigator): LoginEvent
    data class OnEmailUpdated(val email: String): LoginEvent
    data class OnPasswordUpdated(val password: String): LoginEvent

}
