package sv.com.videogametest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import sv.com.videogamestest.di.KoinModules

class VideoGamesApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VideoGamesApplication)
            modules(KoinModules.appModule)
        }
    }
}