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
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions
import java.net.UnknownHostException


@Suppress("PropertyName", "SpellCheckingInspection")
open class BaseViewModel<T : State<T>>(standardState: T) : ViewModel(), KoinComponent{

    protected val context by inject<Context>()

    protected val TAG = standardState.tag

    protected val userDatabase : UserDatabase get() =  UserDatabase.invoke(context)
    protected val userRepository : LocalUserRepository get() =  LocalUserRepository(database = userDatabase)

    /**
     * [UnknownHostException] является ошибкой, в основном когда нет интернета
     *
     **/
    private val handler = CoroutineExceptionHandler { _, exception ->
        if(exception is ViewModelExceptions)castError(exception)
        else when(exception) {
            is UnknownHostException -> castError(ViewModelExceptions.Network)
            else -> castError()
        }
        Log.i("CoroutineException","coroutine exception ${exception.message}")
    }

    protected val _state =  MutableStateFlow(standardState)
    val state = _state.asStateFlow()

    val datastore: CodeDataStore get() = CodeDataStore()

    protected fun castError() {
       _state.update { state.value.onError() }
    }

    protected fun castError(exception: ViewModelExceptions) {
        when(exception) {
            is ViewModelExceptions.Network -> _state.update { state.value.onNetworkError() }
            is ViewModelExceptions.DataValid -> _state.update { state.value.onError() }
        }
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