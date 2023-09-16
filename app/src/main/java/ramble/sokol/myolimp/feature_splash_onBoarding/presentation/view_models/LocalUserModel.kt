package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

@Entity(
    tableName = "user"
)
data class LocalUserModel(
    @PrimaryKey val id: String = "",
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("second_name") val secondName: String? = null,
    @SerializedName("third_name") val thirdName: String? = null,
    @SerializedName("data_of_birth") val dateOfBirth: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("SNILS") val snils: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("grade") val grade: Int? = null,
    @SerializedName("account_type") val accountType: String? = null,
    @SerializedName("image") val profileImg: String? = null,
    val hasThird: Boolean = true,
    @SerializedName("region") @Embedded val region: Region? = null,
    @SerializedName("city") @Embedded val city: City? = null,
    @SerializedName("school") @Embedded val school: School? = null,
)
