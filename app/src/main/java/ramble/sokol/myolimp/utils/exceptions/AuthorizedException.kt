package ramble.sokol.myolimp.utils.exceptions

import java.io.IOException

class AuthorizedException : IOException() {
    override val message: String
        get() = "User is not authenticated"
}
