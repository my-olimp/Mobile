package ramble.sokol.myolimp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class CookiesDataStore(
    val context: Context,
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore(name = "cookies")
    }

    suspend fun setCookies(
        value: String,
        key: String,
    ) {
        val dataStoreKey = stringPreferencesKey(key)

        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    suspend fun deleteCookies() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun getCookies(
        key: String,
    ) : String? {

        val dataStoreKey = stringPreferencesKey(key)

        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey]
    }

}
