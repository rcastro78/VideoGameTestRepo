package sv.com.videogamestest.networking

import retrofit2.Call
import retrofit2.http.GET
import sv.com.videogamestest.model.VideoGameItem

interface IVideoGame {
    @GET("api/games")
    fun getVideoGames(): Call<List<VideoGameItem>>
}