package com.example.nafis.agrosewa.Apiweather

import com.example.nafis.agrosewa.weather.weatherapp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getWeatherData(
        @Query("q")city:String,
        @Query("appid")appid:String,
        @Query("units")units:String
    ):Call<weatherapp>
}