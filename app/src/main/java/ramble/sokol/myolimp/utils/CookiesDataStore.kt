package ramble.sokol.myolimp.utils

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


//class CookiesDataStore2 : KoinComponent {
//
//    companion object {
//        val COOKIES = stringPreferencesKey("COOKIE")
//    }
//
//    private val Context.dataStore: DataStore<Preferences>
//            by preferencesDataStore(name = "cookies")
//
//    private val context by inject<Context>()
//
//    suspend fun setCookies(
//        value: String,
//        key: String,
//    ) {
//        val dataStoreKey = stringPreferencesKey(key)
//
//        context.dataStore.edit {
//            it[dataStoreKey] = value
//        }
//    }
//
//    suspend fun deleteCookies() {
//        context.dataStore.edit {
//            it.clear()
//        }
//    }
//
//    fun getCookies() : Flow<String?> {
//
//        return context.dataStore.data
//            .catch {
//                if (it is IOException) emit(emptyPreferences())
//                else throw it
//            }
//            .map {
//                val cookie = it[COOKIES] ?: null
//                cookie
//            }
//
////        val dataStoreKey = stringPreferencesKey(key)
////
////        val preferences = context.dataStore.data.first()
////
////        return preferences[dataStoreKey]
//    }
//
//}
