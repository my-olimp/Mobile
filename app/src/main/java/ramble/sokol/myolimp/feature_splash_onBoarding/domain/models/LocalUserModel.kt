package ramble.sokol.myolimp.feature_splash_onBoarding.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(
    tableName = "user"
)
data class LocalUserModel(
    @SerializedName("id") @PrimaryKey val id: String = "",
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
    val hasThird: Boolean = true,
    @SerializedName("subjects") val subjects: List<String> = emptyList(),
    @SerializedName("region") @Embedded val region: Region? = null,
    @SerializedName("city") @Embedded val city: City? = null,
    @SerializedName("school") @Embedded val school: School? = null,
)
