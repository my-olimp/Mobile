package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class ResponseSchoolModel(
    @SerializedName("number") val number: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("region") val regionId: Int = -1
) {
    fun asSchool(): School {
        return School(number, name, regionId)
    }
}

data class School (
    val number: Int = -1,
    val name: String = "",
    val regionId : Int = -1
)

fun List<ResponseSchoolModel>.asListSchool(): List<School> {
    val list = mutableListOf<School>()
    for(item in this) list.add(item.asSchool())
    return list
}

fun List<School>.toListString(): List<String> {
    val list = mutableListOf<String>()
    for(item in this) list.add(item.name)
    return list
}