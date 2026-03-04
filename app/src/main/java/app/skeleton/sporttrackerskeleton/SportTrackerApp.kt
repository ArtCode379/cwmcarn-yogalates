package app.skeleton.sporttrackerskeleton

import android.app.Application
import app.skeleton.sporttrackerskeleton.di.dataModule
import app.skeleton.sporttrackerskeleton.di.dispatcherModule
import app.skeleton.sporttrackerskeleton.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SportTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dispatcherModule + dataModule + viewModule

        startKoin {
            androidLogger()
            androidContext(this@SportTrackerApp)
            modules(appModules)
        }
    }
}