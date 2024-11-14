package sv.com.videogametest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.com.videogamestest.db.VideoGameDB
import sv.com.videogamestest.model.VideoGameItem
import sv.com.videogametest.ui.theme.VideoGameTestTheme
import sv.com.videogametest.util.nunitoBold
import sv.com.videogametest.util.nunitoRegular

class VideoGameDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("gameId", 0)

        setContent {
            var videoGame by remember { mutableStateOf<VideoGameItem?>(null) }
            var updatedDescription by remember { mutableStateOf("") }

            LaunchedEffect(id) {
                getVideoGameDetail(id) { game ->
                    videoGame = game
                    updatedDescription = game?.short_description ?: ""
                }
            }

            VideoGameTestTheme {
                VideoGameDetailScreen(
                    onBackClicked = {
                        Intent(this@VideoGameDetailActivity, VideoGamesActivity::class.java).also {
                            startActivity(it)
                        }
                        finish()
                    },
                    onDeleteClicked = {
                        Toast.makeText(applicationContext, "Juego eliminado", Toast.LENGTH_SHORT).show()
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = VideoGameDB.getInstance(this@VideoGameDetailActivity)
                            db.iVideoGameDAO().delete(id)
                        }
                        Intent(this@VideoGameDetailActivity, VideoGamesActivity::class.java).also {
                            startActivity(it)
                        }
                        finish()
                    },
                    onSaveDescriptionClicked = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = VideoGameDB.getInstance(this@VideoGameDetailActivity)
                            db.iVideoGameDAO().updateShortDescription(updatedDescription, videoGame?.id ?: 0)
                        }
                    },
                    videoGame = videoGame,
                    updatedDescription = updatedDescription,
                    onDescriptionChanged = { newDescription ->
                        updatedDescription = newDescription
                    }
                )
            }
        }
    }

    @Composable
    fun VideoGameDetailScreen(
        onBackClicked: () -> Unit,
        onDeleteClicked: () -> Unit,
        onSaveDescriptionClicked: () -> Unit,
        videoGame: VideoGameItem?,
        updatedDescription: String,
        onDescriptionChanged: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(videoGame?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = videoGame?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(bottom = 16.dp)
            )

            Text(
                text = videoGame?.title ?: "Unknown Title",
                fontFamily = nunitoBold,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Género
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.gamepad),
                    contentDescription = "Genre",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    fontFamily = nunitoRegular,
                    text = "Género: "+videoGame?.genre ?: "Unknown Genre")
            }

            // Género
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.world),
                    contentDescription = "Publisher",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    fontFamily = nunitoRegular,
                    text = "Publicado por: "+videoGame?.publisher ?: "Unknown Publisher")
            }

            // Género
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.monitor),
                    contentDescription = "Platform",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    fontFamily = nunitoRegular,
                    text = "Plataforma: "+videoGame?.platform ?: "Unknown Platform")
            }

            // Descripción corta editable
            OutlinedTextField(
                value = updatedDescription,  // Usar el valor actualizado
                onValueChange = onDescriptionChanged,  // Actualizar cuando cambie el texto
                label = { Text("Descripción corta (editable)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = false,
                maxLines = 4
            )
            Spacer(modifier = Modifier.weight(1f))
            // Botones
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botón de volver
                Button(
                    onClick = onBackClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Volver",
                        fontFamily = nunitoRegular)
                }

                // Botón de eliminar
                Button(
                    onClick = onDeleteClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Eliminar",
                        fontFamily = nunitoRegular)
                }

                // Botón de guardar cambios en la descripción
                Button(
                    onClick = onSaveDescriptionClicked,  // Guardar cambios
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Guardar",
                        fontFamily = nunitoRegular)
                }
            }
        }
    }

    fun getVideoGameDetail(id: Int, onGameFetched: (VideoGameItem?) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = VideoGameDB.getInstance(this@VideoGameDetailActivity)
                val v = db.iVideoGameDAO().get(id)

                withContext(Dispatchers.Main) {
                    if (v != null) {

                        onGameFetched(
                            VideoGameItem(
                                id = v.id,
                                title = v.title,
                                thumbnail = v.thumbnail,
                                developer = v.developer,
                                genre = v.genre,
                                platform = v.platform,
                                publisher = v.publisher,
                                release_date = v.release_date,
                                short_description = v.short_description,
                                freetogame_profile_url = v.freetogame_profile_url,
                                game_url = v.game_url
                            )
                        )
                    } else {
                        Log.e("VideoGameDetailActivity", "No video game found with id $id")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("VideoGameDetailActivity", "Error fetching video game details", e)
                }
            }
        }
    }
}
