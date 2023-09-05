package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class ResponseSchoolModel(
    @SerializedName("number") val number: Int = -1,
    @SerializedName("name") val name: String = ""
) {
    fun asSchool(): School {
        return School(number, name)
    }
}

data class RequestSchoolModel (
    @SerializedName("region") val regionId: Int
)

data class School (
    val number: Int = -1,
    val name: String = ""
) {
    fun asResponseModel() : ResponseSchoolModel {
        return ResponseSchoolModel(number, name)
    }
}

fun List<ResponseSchoolModel>.asListSchool(): List<School> {
    val list = mutableListOf<School>()
    for(item in this) list.add(item.asSchool())
    return list
}
