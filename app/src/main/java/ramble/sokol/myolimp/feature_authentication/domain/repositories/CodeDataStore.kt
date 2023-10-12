package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class CodeDataStore : KoinComponent {

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val COOKIES = stringPreferencesKey("COOKIE")

        private val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore(name = "token")
    }

    private val context by inject<Context>()

    suspend fun setToken(
        key: Preferences.Key<String>,
        value: String
    ) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit {
            it.clear()
        }
    }

    fun getToken(
        key: Preferences.Key<String>
    ): Flow<String?> {
        return context.dataStore.data
            .catch {
                if (it is IOException) emit(emptyPreferences())
                else throw it
            }
            .map {
                val cookie = it[key]
                cookie
            }
    }
}
