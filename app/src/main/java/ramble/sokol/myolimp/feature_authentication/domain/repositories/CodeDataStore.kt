package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class CodeDataStore (
    val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore(name = "token")
    }

    suspend fun setToken(
        key: String,
        value: String
    ) {

        val dataStoreKey = stringPreferencesKey(key)

        context.dataStore.edit {
            it[dataStoreKey] = value
        }

    }


    suspend fun getToken(
        key: String
    ): String {

        val dataStoreKey = stringPreferencesKey(key)

        val preferences = context.dataStore.data.first()

        return preferences[dataStoreKey] ?: LocalDate.now().toString()

    }

}
