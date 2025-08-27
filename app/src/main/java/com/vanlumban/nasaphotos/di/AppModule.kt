package com.vanlumban.nasaphotos.di

import com.vanlumban.nasaphotos.data.repository.NasaRepository
import com.vanlumban.nasaphotos.data.service.NasaApiService
import com.vanlumban.nasaphotos.ui.screen.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(NasaApiService::class.java) }

    single { NasaRepository(get()) }

    viewModel { HomeViewModel(get()) }
}
