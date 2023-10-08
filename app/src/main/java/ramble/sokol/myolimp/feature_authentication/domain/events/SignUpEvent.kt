package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface SignUpEvent {

    data object OnClosedSnackBar : SignUpEvent
    data class OnSignUp(val navigator: DestinationsNavigator): SignUpEvent
    data class OnEmailUpdated(val email: String): SignUpEvent
    data class OnPasswordUpdated(val password: String): SignUpEvent
    data class OnConfirmedPasswordUpdated(val confirmedPassword: String): SignUpEvent
    data class OnCode1Updated(
        val navigator: DestinationsNavigator,
        val code1: Int?
    ): SignUpEvent
    data class OnCode2Updated(
        val navigator: DestinationsNavigator,
        val code2: Int?
    ): SignUpEvent
    data class OnCode3Updated(
        val navigator: DestinationsNavigator,
        val code3: Int?
    ): SignUpEvent
    data class OnCode4Updated(
        val navigator: DestinationsNavigator,
        val code4: Int?
    ): SignUpEvent
    data class OnCode5Updated(
        val navigator: DestinationsNavigator,
        val code5: Int?
    ): SignUpEvent
    data class OnCode6Updated(
        val navigator: DestinationsNavigator,
        val code6: Int?
    ): SignUpEvent

}
