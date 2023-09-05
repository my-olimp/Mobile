package ramble.sokol.myolimp.feature_authentication.data.api

import android.content.Context
import okhttp3.OkHttpClient
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.utils.interceptors.NetworkConnectionInterceptor
import ramble.sokol.myolimp.utils.interceptors.ReceivedCookiesInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(
    context: Context
) {

    private val client = OkHttpClient.Builder()
//        .addInterceptor(SetCookiesInterceptor(context = context))
        // get cookie
        .addInterceptor(ReceivedCookiesInterceptor(context = context))
        // if there is network connection
        .addInterceptor(NetworkConnectionInterceptor(context = context))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> instance(service: Class<T>): T {
        return retrofit.create(service)
    }

}
