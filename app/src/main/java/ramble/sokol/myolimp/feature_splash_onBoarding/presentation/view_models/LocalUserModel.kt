package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

@Entity(
    tableName = "user"
)
data class LocalUserModel(
    @PrimaryKey val id: String = "",
    val firstName: String? = null,
    val secondName: String? = null,
    val thirdName: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val snils: String? = null,
    @Embedded val region: Region? = null,
    @Embedded val city: City? = null,
    @Embedded val school: School? = null,
    val phone: String? = null,
    val email: String? = null,
    val grade: Int? = null,
    val accountType: String? = null,
    val profileImg: String? = null,
    val hasThird: Boolean? = null,
)
