package ramble.sokol.myolimp.feature_profile.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ramble.sokol.myolimp.feature_profile.data.Constants.BASE_URL
import ramble.sokol.myolimp.utils.interceptors.NetworkConnectionInterceptor
import ramble.sokol.myolimp.utils.interceptors.ReceivedCookiesInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRetrofitInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        // get cookie
        .addInterceptor(ReceivedCookiesInterceptor())

        // if there is network connection
        .addInterceptor(NetworkConnectionInterceptor())

        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> instance(service: Class<T>): T {
        return retrofit.create(service)
    }
}
