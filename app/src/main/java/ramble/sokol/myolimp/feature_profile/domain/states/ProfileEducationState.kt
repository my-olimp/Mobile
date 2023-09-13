package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

data class ProfileEducationState(
    val region: Region = Region(),
    val city: City = City(),
    val school: School = School(),
    val grade: Int = 0
)