package ramble.sokol.myolimp.feature_authentication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseCityModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("region") val regionId: Int = -1
) {
    fun asCity(): City {
        return City(id, name, regionId)
    }
}

@Entity
data class City(
    @PrimaryKey @ColumnInfo("city_id") val id: Int = -1,
    @ColumnInfo("city_name") val name: String = "",
    @ColumnInfo("city_region_id") val regionId: Int = -1
)

fun List<ResponseCityModel>.asListCity(): List<City> {
    val list = mutableListOf<City>()
    for(item in this) list.add(item.asCity())
    return list
}

fun List<City>.toListString(): List<String> {
    val list = mutableListOf<String>()
    for(item in this) list.add(item.name)
    return list
}