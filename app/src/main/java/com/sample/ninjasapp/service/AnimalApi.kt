package com.sample.ninjasapp.service

import com.sample.ninjasapp.model.Animal
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AnimalApi {
    @Headers("X-Api-Key: XB2xaIrruYztlhnd+h3yCw==bvpvbo1A33T3ptEn")
    @GET("v1/animals")
    suspend fun getAnimals(@Query("name") name: String): List<Animal>
}