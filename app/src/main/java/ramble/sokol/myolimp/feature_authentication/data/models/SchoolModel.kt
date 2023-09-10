package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class ResponseSchoolModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("region") val regionId: Int = -1
) {
    fun asSchool(): School {
        return School(id, name, regionId)
    }
}

data class School (
    val id: Int = -1,
    val name: String = "",
    val regionId : Int = -1
) {
    fun asResponseModel(): ResponseSchoolModel{
        return ResponseSchoolModel(id, name, regionId)
    }
}


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