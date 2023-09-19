package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ramble.sokol.myolimp.feature_authentication.data.models.Region

@ProvidedTypeConverter
class LocalUserConverter {
    @TypeConverter
    fun regionToString(value: Region?): String? {
        return "${value?.number}-${value?.name}"
    }

    @TypeConverter
    fun stringToRegion(value: Region?): String? {
        return "${value?.number}-${value?.name}"
    }
}