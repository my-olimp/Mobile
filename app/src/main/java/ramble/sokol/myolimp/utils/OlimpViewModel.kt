package ramble.sokol.myolimp.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


@Suppress("PropertyName")
open class OlimpViewModel<T : State<T>>(standardState: T) : ViewModel(){

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
     * Прокидывает в CoroutineExceptionHandler ошибку только если это происходит
     * непосредственно в скопе.
     *
     * При выбрасывании ошибки лямбды, приложение падает.
     **/
    protected fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(
            context = handler + Dispatchers.IO,
            block = block
        )
    }

}