package ramble.sokol.myolimp.feature_authentication.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity
data class School (
    @PrimaryKey @ColumnInfo("school_id") val id: Int = -1,
    @ColumnInfo("school_name") val name: String = "",
    @ColumnInfo("school_region_id")val regionId : Int = -1
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