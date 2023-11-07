package kz.just_code.retorfitapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiData {
    private const val baseUrl = "https://api.weatherapi.com/v1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): WeatherApi {
        return getRetrofit()
            .create(WeatherApi::class.java)
    }
}