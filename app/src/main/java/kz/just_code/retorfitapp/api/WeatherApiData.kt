package kz.just_code.retorfitapp.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@InstallIn(SingletonComponent::class)
@Module
object WeatherApiData {

    @Provides
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getApi(): WeatherApi {
        return getRetrofit()
            .create(WeatherApi::class.java)
    }
}