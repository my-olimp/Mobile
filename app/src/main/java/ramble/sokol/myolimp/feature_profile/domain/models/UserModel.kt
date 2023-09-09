package ramble.sokol.myolimp.feature_profile.domain.models

import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity

data class UserModel(
    val id: String = "",
    val firstName: String = "",
    val secondName: String = "",
    val thirdName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val snils: String = "",
    val region: ResponseRegionModel = ResponseRegionModel(),
    val city: ResponseCityModel = ResponseCityModel(),
    val school: ResponseSchoolModel = ResponseSchoolModel(),
    val phone: String = "",
    val email: String = "",
    val grade: Int = 0,
    val accountType: String = "",
    val profileImg: String? = null,
    val hasThird: Boolean = true,
) {
    fun toUserModelEntity() = UserModelEntity(
        firstName = if (firstName == "") null else firstName,
        secondName = if (secondName == "") null else secondName,
        thirdName = if (thirdName == "") null else thirdName,
        dateOfBirth = if (dateOfBirth == "") null else dateOfBirth,
        gender = when(gender) {
            "Мужской" -> "m"
            "Женский" -> "f"
            else -> null
        },
        snils = if (snils == "") null else snils,
        region = region,
        city = city,
        school = school,
        phone = if (phone == "") null else phone,
        email = if (email == "") null else email,
        grade = if (grade == 0) null else grade,
        accountType = if (accountType == "") null else accountType,
        profileImg = profileImg
    )
}
