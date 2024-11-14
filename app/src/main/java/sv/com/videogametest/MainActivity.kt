package sv.com.videogametest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

import sv.com.videogamestest.db.VideoGameDB
import sv.com.videogamestest.db.VideoGameEntity
import sv.com.videogamestest.viewmodel.VideoGameViewModel
import sv.com.videogametest.ui.theme.VideoGameTestTheme
import sv.com.videogametest.util.nunitoBold

class MainActivity : ComponentActivity() {
    private val videoGameViewModel: VideoGameViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = VideoGameDB.getInstance(this)
        videoGameViewModel.getVideoGamesList(OnSuccess = {
            CoroutineScope(Dispatchers.IO).launch {
                it.forEach { video->
                    val existingGame = db.iVideoGameDAO().get(video.id)
                    if (existingGame == null) {
                        val videoGame = VideoGameEntity(0,video.id,video.title,
                            video.freetogame_profile_url,video.game_url,video.genre,video.thumbnail,
                            video.short_description,video.platform,video.publisher,video.developer,
                            video.release_date)

                        db.iVideoGameDAO().insert(videoGame)
                    }
                }
            }
        },
            OnError = {

            })
        setContent {
            VideoGameTestTheme {
                if (videoGameViewModel.isLoading) {
                    SplashScreen()
                }else{
                   Thread.sleep(1000)
                   Intent(this@MainActivity, VideoGamesActivity::class.java).also{
                       startActivity(it)
                   }
                }

            }
        }
    }


    @Composable
    fun SplashScreen() {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Descargando listado...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontFamily = nunitoBold,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 72.dp)
                )

                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 18.dp)
                )
            }
        }

}

