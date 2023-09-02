package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.util.RegistationEvent

class RegisterInfoViewModel(
    val context: Context
) : ViewModel() {

    fun onEvent(
        event: RegistationEvent
    ) {
        when(event) {
            is RegistationEvent.onActivityTypeChanged -> {

            }
            is RegistationEvent.onDobChanded -> {

            }
            is RegistationEvent.onGenderChanged -> {

            }
            is RegistationEvent.onNameSurnameChanged -> {

            }
            is RegistationEvent.OnSave -> {

            }
        }
    }
}