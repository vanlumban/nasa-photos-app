package com.vanlumban.nasaphotos.data.repository

import com.vanlumban.nasaphotos.BuildConfig
import com.vanlumban.nasaphotos.data.models.NasaPhoto
import com.vanlumban.nasaphotos.data.service.NasaApiService

class NasaRepository(private val api: NasaApiService) {
    suspend fun getRandomPhotos(): List<NasaPhoto> {
        return api.getRandomPhotos(apiKey = BuildConfig.NASA_API_KEY)
    }
}
