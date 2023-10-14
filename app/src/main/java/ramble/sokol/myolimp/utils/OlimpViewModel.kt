package ramble.sokol.myolimp.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository


@Suppress("PropertyName", "SpellCheckingInspection")
open class OlimpViewModel<T : State<T>>(standardState: T) : ViewModel(), KoinComponent{

    protected val context by inject<Context>()

    private val userDatabase : UserDatabase get() =  UserDatabase.invoke(context)
    protected val userRepository : LocalUserRepository get() =  LocalUserRepository(database = userDatabase)

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.i("CoroutineException","coroutine exception ${exception.message}")
        castError()
    }

    protected val _state =  MutableStateFlow(standardState)
    val state = _state.asStateFlow()

    val datastore: CodeDataStore get() = CodeDataStore()

    protected fun castError() {
       _state.update { state.value.onError() }
    }

    protected fun updateLoader(value: Boolean) {
        _state.update { state.value.onLoaderUpdate(value) }
    }
    /**
     * Прокидывает в [CoroutineExceptionHandler] ошибку только если это происходит
     * непосредственно в скопе.
     *
     * При выбрасывании ошибки лямбды, приложение падает.
     *
     * Хоть и [Thread.currentThread] показывает что текущий поток [Dispatchers.Default], в контексте
     * зашит [Dispatchers.IO], и может быть на самом деле она работает на потоке IO, просто
     * для операций логирования вызвается дефолт поток
     **/
    protected fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(
            context = handler + Dispatchers.IO,
            block = block
        )
    }

}