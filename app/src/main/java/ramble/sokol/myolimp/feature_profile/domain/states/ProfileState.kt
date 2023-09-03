package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.data.models.UserModel

data class ProfileState(
    val user: UserModel = UserModel(),
)
