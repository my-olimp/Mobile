package ramble.sokol.myolimp.utils.interceptors

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

        Log.i(TAG, "send request")

        val originalRequest = chain.request()

        val accessToken = getAccessToken()

        Log.i(TAG, "access - $accessToken")

        var requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")

        var newRequest = requestBuilder.build()

        val response = chain.proceed(newRequest)

        Log.i(TAG, "response code - ${response.code}")

        if (response.code == 402 || response.code == 401) {
            runBlocking {

                Log.i(TAG, "update token")

                val token = getRefreshToken()

                Log.i(TAG, "refresh - $token")

                val result = ProfileRepository().refreshTokenNew(
                    token ?: throw Exception("no refresh")
                )

                saveCookies(result.body()?.code ?: throw AuthorizedException())

                //val newToken = result.body()?.code ?: throw AuthorizedException()

                Log.i(TAG, "newToken - ${getRefreshToken()}")

                requestBuilder = originalRequest.newBuilder()
                    .header("Cookie", "Bearer ${getRefreshToken()}")

                newRequest = requestBuilder.build()
            }

            return chain.proceed(newRequest)
        }

        return response
    }

    private fun getAccessToken() : String?
        = runBlocking {
        return@runBlocking CodeDataStore().getToken(ACCESS_TOKEN).first<String?>()
    }

    private fun saveCookies(
        cookie: String
    ) = runBlocking {
        CodeDataStore().setToken(
            value = cookie,
            key = COOKIES
        )
    }

    private fun getRefreshToken() : String?
        = runBlocking {
        return@runBlocking CodeDataStore().getToken(COOKIES).first<String?>()
    }

}
