package ramble.sokol.myolimp.utils.interceptors

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.COOKIES
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.utils.exceptions.AuthorizedException
import java.io.IOException

class AuthorizedInterceptor : Interceptor {

    companion object {
        const val TAG = "InterceptorAuthorized"
    }

    @Throws(IOException::class)
    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {

        Log.i(TAG, "send request - ${chain.request().url} - ${chain.request().headers}")

        val originalRequest = chain.request()

        val accessToken = getAccessToken()

        Log.i(TAG, "access - $accessToken")

        var requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")

        val response = chain.proceed(requestBuilder.build())

        Log.i(TAG, "response code - ${response.code}")

        if (response.code >= 400) {
            // to close previous
            response.close()

            Log.i(TAG, "update token")

            val token = getRefreshToken()

            Log.i(TAG, "refresh - $token")

            runBlocking {

                val currentThread = Thread.currentThread()
                if (currentThread == Looper.getMainLooper().thread) {
                    Log.i(TAG, "Coroutine is running on the main thread")
                } else {
                    Log.i(TAG, "Coroutine is running on a background thread: ${currentThread.name}")
                }

                val result = ProfileRepository().refreshToken(
                    cookie = token ?: throw Exception("no cookie")
                )

                Log.i(TAG, "new refresh body - ${result.body()}")

                val access = result.body()?.code

                saveAccess(
                    access ?: throw AuthorizedException()
                )

                Log.i(TAG, "newToken - $access")

                requestBuilder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $access")

            }

            Log.i(TAG, "returning 2")

            return chain.proceed(requestBuilder.build())

        }

        Log.i(TAG, "returning 1")

        return response
    }

    private fun getAccessToken() : String?
        = runBlocking {
            return@runBlocking CodeDataStore().getToken(ACCESS_TOKEN).first<String?>()
    }

    private fun saveAccess(
        access: String
    ) = runBlocking {
        CodeDataStore().setToken(
            value = access,
            key = ACCESS_TOKEN
        )
    }

    private fun getRefreshToken() : String?
        = runBlocking {
        return@runBlocking CodeDataStore().getToken(COOKIES).first<String?>()
    }

}
