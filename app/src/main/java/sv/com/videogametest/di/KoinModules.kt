package sv.com.videogamestest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sv.com.videogamestest.networking.IVideoGame
import sv.com.videogamestest.networking.VideoGameAPI
import sv.com.videogamestest.viewmodel.VideoGameViewModel


object KoinModules {
    val appModule = module {
        single<IVideoGame> {
            VideoGameAPI.getVideoGameService()!! }
        viewModel { VideoGameViewModel(get()) }
    }




}