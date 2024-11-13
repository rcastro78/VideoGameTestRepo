package sv.com.videogamestest.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import sv.com.videogamestest.model.VideoGameItem
import sv.com.videogamestest.networking.IVideoGame

class VideoGameViewModel(private val iVideoGame: IVideoGame):ViewModel(){
    var isLoading by mutableStateOf(true)
        private set
    fun getVideoGamesList(OnSuccess:(List<VideoGameItem>)->Unit, OnError:()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val result = iVideoGame.getVideoGames().awaitResponse()
            if (result.isSuccessful){
                val videoGames = result.body()
                OnSuccess(videoGames!!)
                isLoading = false
            }else{
                OnError()
                isLoading = true
            }
        }
    }
}