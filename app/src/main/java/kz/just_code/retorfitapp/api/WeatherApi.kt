package kz.just_code.retorfitapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json?key=bf4dc83bbe114579817143928230611")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("lang") language: String
    ): Response<WeatherResponse>
}