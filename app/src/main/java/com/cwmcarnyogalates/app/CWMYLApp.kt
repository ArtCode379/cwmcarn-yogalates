package com.cwmcarnyogalates.app

import android.app.Application
import com.cwmcarnyogalates.app.di.dataModule
import com.cwmcarnyogalates.app.di.dispatcherModule
import com.cwmcarnyogalates.app.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CWMYLApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CWMYLApp)
            modules(dispatcherModule + dataModule + viewModule)
        }
    }
}
