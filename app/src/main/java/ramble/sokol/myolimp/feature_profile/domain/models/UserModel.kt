package ramble.sokol.myolimp.feature_profile.domain.models

import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity

data class UserModel(
    val id: String = "",
    val firstName: String = "",
    val secondName: String = "",
    val thirdName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val snils: String = "",
    val region: Region = Region(),
    val city: City = City(),
    val school: School = School(),
    val phone: String = "",
    val email: String = "",
    val grade: Int = 0,
    val accountType: String = "",
    val profileImg: String? = null,
    val hasThird: Boolean = true,
    val regionList: List<Region> = listOf(),
    val cityList: List<City> = listOf(),
    val schoolList: List<School> = listOf()
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
        region = region.asResponseModel(),
        city = city.asResponseModel(),
        school = school.asResponseModel(),
        phone = if (phone == "") null else phone,
        email = if (email == "") null else email,
        grade = if (grade == 0) null else grade,
        accountType = if (accountType == "") null else accountType,
        profileImg = profileImg
    )
}
