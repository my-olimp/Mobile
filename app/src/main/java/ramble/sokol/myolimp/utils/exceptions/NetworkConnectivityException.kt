package ramble.sokol.myolimp.utils.exceptions

import java.io.IOException

class NetworkConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}
