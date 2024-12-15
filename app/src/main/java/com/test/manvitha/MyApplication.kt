package com.test.manvitha

import android.app.Application
import com.test.manvitha.di.appModule
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}