package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class ResponseCityModel(
    @SerializedName("number") val number: Int = -1,
    @SerializedName("name") val name: String = ""
) {
    fun asCity(): City {
        return City(number, name)
    }
}

data class RequestCityModel(
    @SerializedName("region") val regionId: Int
)

data class City(
    val number: Int = -1,
    val name: String = ""
) {
    fun asResponseModel() : ResponseCityModel {
        return ResponseCityModel(number, name)
    }
}

fun List<ResponseCityModel>.asListCity(): List<City> {
    val list = mutableListOf<City>()
    for(item in this) list.add(item.asCity())
    return list
}
