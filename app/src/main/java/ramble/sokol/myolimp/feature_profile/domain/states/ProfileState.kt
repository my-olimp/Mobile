package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserModel

data class ProfileState (
    val user: LocalUserModel = LocalUserModel()
)
