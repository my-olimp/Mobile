package ramble.sokol.myolimp.feature_profile.data.models

data class RequestUserModel(
    val id: Int = -1,
    val firstName: String? = null,
    val secondName: String? = null,
    val thirdName: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val snils: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val grade: Int? = null,
    val accountType: String? = null,
    val profileImage: String? = null,
    val regionId: Int? = null,
    val cityId: Int? = null,
    val schoolId: Int? = null
    )