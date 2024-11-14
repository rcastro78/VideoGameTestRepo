package sv.com.videogametest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sv.com.videogamestest.composable.VideoGameItemComposable
import sv.com.videogamestest.db.VideoGameDB
import sv.com.videogamestest.model.VideoGameItem
import sv.com.videogametest.ui.theme.VideoGameTestTheme

class VideoGamesActivity : ComponentActivity() {

    var lstVideoGames = mutableStateListOf<VideoGameItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getVideoGames()
        setContent {
            VideoGameTestTheme {
                VideoGamesScreen()
            }
        }
    }

    @Composable
    fun VideoGamesScreen() {
        var searchText by remember { mutableStateOf("") }
        val filteredList = if (searchText.isNotEmpty()) {
            lstVideoGames.filter {
                it.title.contains(searchText, ignoreCase = true) || it.genre.contains(searchText, ignoreCase = true)
            }
        } else {
            lstVideoGames
        }

        Column(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Listado de Juegos",
                color = Color.Blue,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            SearchBarVideoGame(
                searchText,
                onSearchTextChange = { searchText = it }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(filteredList.sortedBy { it.title }) { index, item ->
                    VideoGameItemComposable(item.thumbnail, item.title,
                        item.id, onItemClicked = {
                            val intent = Intent(this@VideoGamesActivity, VideoGameDetailActivity::class.java)
                            intent.putExtra("gameId", it)
                            startActivity(intent)
                            finish()
                        })
                }
            }
        }
    }
    @Composable
    fun SearchBarVideoGame(
        searchText: String = "",
        onSearchTextChange: (String) -> Unit = {}
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Buscar por género o nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )
    }

    // Función para obtener los juegos desde la base de datos
    fun getVideoGames() {
        // Limpiar la lista para asegurarse de que está vacía antes de añadir nuevos elementos
        lstVideoGames.clear()

        val db = VideoGameDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val videoGames = db.iVideoGameDAO().getAll()
            Log.d("VideoGamesActivity", "Total de juegos recuperados: ${videoGames.size}")

            // Agregar los juegos a la lista lstVideoGames
            videoGames.forEach { v ->
                val videoGameItem = VideoGameItem(
                    v.developer, v.freetogame_profile_url,
                    v.game_url, v.genre, v.id, v.platform, v.publisher, v.release_date,
                    v.short_description, v.thumbnail, v.title
                )
                // Usamos `withContext(Dispatchers.Main)` para actualizar la lista en el hilo principal
                withContext(Dispatchers.Main) {
                    lstVideoGames.add(videoGameItem)
                }
            }
        }
    }
}
