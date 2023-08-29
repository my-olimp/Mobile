package ramble.sokol.myolimp.feature_authentication.data.api

import okhttp3.OkHttpClient
import ramble.sokol.myolimp.feature_profile.data.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> instance(service: Class<T>): T{
        return retrofit.create(service)
    }
}
