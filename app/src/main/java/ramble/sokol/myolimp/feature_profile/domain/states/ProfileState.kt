package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity

data class ProfileState(
    val user: UserModelEntity = UserModelEntity(),
)
