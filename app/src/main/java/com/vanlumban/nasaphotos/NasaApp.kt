package com.vanlumban.nasaphotos

import android.app.Application
import com.vanlumban.nasaphotos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NasaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NasaApp)
            modules(appModule)
        }
    }
}
