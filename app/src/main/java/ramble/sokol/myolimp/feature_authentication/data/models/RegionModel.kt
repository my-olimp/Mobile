package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class ResponseRegionModel(
    @SerializedName("number") val number: Int = -1,
    @SerializedName("name") val name: String = ""
) {
    fun asRegion(): Region {
        return Region(number, name)
    }
}

data class Region(
    val number: Int = -1,
    val name: String = ""
) {
    fun asResponseModel(): ResponseRegionModel {
        return ResponseRegionModel(number, name)
    }
}

fun List<ResponseRegionModel>.asListRegion(): List<Region> {
    val list = mutableListOf<Region>()
    for(item in this) list.add(item.asRegion())
    return list
}

fun List<Region>.toListString(): List<String> {
    val list = mutableListOf<String>()
    for(item in this) list.add(item.name)
    return list
}