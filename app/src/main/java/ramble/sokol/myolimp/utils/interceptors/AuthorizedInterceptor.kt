package ramble.sokol.myolimp.utils.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ramble.sokol.myolimp.utils.exceptions.AuthorizedException
import java.io.IOException

class AuthorizedInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {
        val response = chain.proceed(chain.request())

        // not authorized
        if (response.code == 402) {
            throw AuthorizedException()
        }

        return response
    }
}
