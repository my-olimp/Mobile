package ramble.sokol.myolimp.feature_authentication.domain.events

import android.graphics.Bitmap
import android.net.Uri
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File

sealed interface RegistrationImageEvent {
    data class OnChangedScreenType(val isWorkScreen: Boolean) : RegistrationImageEvent

    data class OnSnilsChanged(val snils: String) : RegistrationImageEvent
    data class OnImageChanged(val uri: Uri?) : RegistrationImageEvent
    data class OnSubmit(val navigator: DestinationsNavigator) : RegistrationImageEvent
}
