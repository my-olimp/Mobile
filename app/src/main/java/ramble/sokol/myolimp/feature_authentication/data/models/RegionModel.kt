package ramble.sokol.myolimp.feature_authentication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseRegionModel(
    @SerializedName("number") val number: Int = -1,
    @SerializedName("name") val name: String = ""
) {
    fun asRegion(): Region {
        return Region(number, name)
    }
}

@Entity
data class Region(
    @PrimaryKey val number: Int = -1,
    @ColumnInfo("region_name") val name: String = ""
)

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
