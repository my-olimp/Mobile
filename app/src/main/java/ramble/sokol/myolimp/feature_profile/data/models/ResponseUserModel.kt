package ramble.sokol.myolimp.feature_profile.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel

data class ResponseUserModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("second_name") val secondName: String? = null,
    @SerializedName("third_name") val thirdName: String? = null,
    @SerializedName("data_of_birth") val dateOfBirth: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("SNILS") val snils: String? = null,
    @SerializedName("region") val region: ResponseRegionModel? = null,
    @SerializedName("city") val city: ResponseCityModel? = null,
    @SerializedName("school") val school: ResponseSchoolModel? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("grade") val grade: Int? = null,
    @SerializedName("account_type") val accountType: String? = null,
    @SerializedName("image") val profileImg: String? = null
)
