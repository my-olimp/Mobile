package ramble.sokol.myolimp.feature_splash_onBoarding.utils.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
//            Gson().fromJson(value, Array<String>::class.java)
            Gson().fromJson(value, Array<String>::class.java).toList() //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}
