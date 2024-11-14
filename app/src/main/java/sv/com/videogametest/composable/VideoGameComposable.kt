package sv.com.videogamestest.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sv.com.videogametest.util.nunitoBold

@Composable
@Preview(showBackground = true)
fun VideoGameItemComposable(thumbNailUrl:String="",title:String="",
                            gameId:Int=0,
                            onItemClicked: (Int) -> Unit={}){
    Column(
        modifier = Modifier
            .padding(16.dp) // Espaciado
            .fillMaxWidth() // Ancho completo
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)) // Fondo con bordes redondeados
            .padding(16.dp) // Espaciado interno
            .clickable { onItemClicked(gameId) }
    ) {
        if (thumbNailUrl.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbNailUrl)
                    .crossfade(true) // Activar transiciones suaves
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 8.dp), // Espaciado debajo de la imagen
                //placeholder = painterResource(id = R.drawable.placeholder_image), // Placeholder (opcional)
                //error = painterResource(id = R.drawable.error_image) // Imagen de error (opcional)
            )
        }

        // Título del videojuego
        Text(
            text = title,
            fontFamily = nunitoBold,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}