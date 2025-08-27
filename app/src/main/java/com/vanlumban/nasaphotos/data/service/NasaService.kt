package com.vanlumban.nasaphotos.data.service

import com.vanlumban.nasaphotos.data.models.NasaPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    suspend fun getRandomPhotos(
        @Query("api_key") apiKey: String = "DEMO_KEY",
        @Query("count") count: Int = 10
    ): List<NasaPhoto>
}
