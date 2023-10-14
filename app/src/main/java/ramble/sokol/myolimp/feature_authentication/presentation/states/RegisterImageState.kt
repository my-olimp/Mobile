package ramble.sokol.myolimp.feature_authentication.presentation.states

import android.net.Uri
import ramble.sokol.myolimp.utils.State

data class RegisterImageState(
    val snils: String = "",
    val profileImg: Uri? = null,

    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isWorkScreen: Boolean = false,
) : State<RegisterImageState> {
    override fun onError(): RegisterImageState {
        return this.copy(isError = true)
    }

    override fun onLoaderUpdate(value: Boolean): RegisterImageState {
        return this.copy(isLoading = value)
    }

}
