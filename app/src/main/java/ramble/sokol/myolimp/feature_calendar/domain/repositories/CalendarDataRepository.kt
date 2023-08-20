package ramble.sokol.myolimp.feature_calendar.domain.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class CalendarDataRepository (
    val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore(name = "calendar")
    }

    suspend fun isCreatedPlan(
        key: String,
        value: Boolean
    ) {

        val dataStoreKey = booleanPreferencesKey(key)

        context.dataStore.edit {
            it[dataStoreKey] = value
        }

    }

    suspend fun setPreviousDate(
        key: String,
        value: String
    ) {

        val dataStoreKey = stringPreferencesKey(key)

        context.dataStore.edit {
            it[dataStoreKey] = value
        }

    }

    suspend fun getStatusCreating(
        key: String
    ): Boolean {

        val dataStoreKey = booleanPreferencesKey(key)

        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey] ?: false

    }

    suspend fun getPreviousDate(
        key: String
    ): String {

        val dataStoreKey = stringPreferencesKey(key)

        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey] ?: LocalDate.now().toString()

    }

}
