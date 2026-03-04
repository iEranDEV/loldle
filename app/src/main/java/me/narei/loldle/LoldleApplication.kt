package me.narei.loldle

import android.app.Application
import me.narei.loldle.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoldleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LoldleApplication)
            modules(appModule)
        }
    }

}