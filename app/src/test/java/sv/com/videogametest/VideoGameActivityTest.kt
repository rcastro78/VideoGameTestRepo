package sv.com.videogametest


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import sv.com.videogamestest.model.VideoGameItem

class VideoGamesActivityTest {

    private lateinit var activity: VideoGamesActivity

    @Before
    fun setup() {
        activity = VideoGamesActivity()
        activity.lstVideoGames = mutableListOf(
            VideoGameItem("Developer1", "url1", "game_url1", "Action", 1, "PC", "Publisher1", "2022-01-01", "Description1", "thumb1", "Game1"),
            VideoGameItem("Developer2", "url2", "game_url2", "Adventure", 2, "PC", "Publisher2", "2022-01-02", "Description2", "thumb2", "Game2"),
            VideoGameItem("Developer3", "url3", "game_url3", "Action", 3, "PC", "Publisher3", "2022-01-03", "Description3", "thumb3", "Game3")
        )
    }

    @Test
    fun `filter games by genre or title`() {
        // Filtrar por título
        var searchText = "Game1"
        val filteredListTitle = activity.lstVideoGames.filter {
            it.title.contains(searchText, ignoreCase = true) || it.genre.contains(searchText, ignoreCase = true)
        }
        assertThat(filteredListTitle.size).isEqualTo(1)

        // Filtrar por género
        searchText = "Action"
        val filteredListGenre = activity.lstVideoGames.filter {
            it.title.contains(searchText, ignoreCase = true) || it.genre.contains(searchText, ignoreCase = true)
        }
        assertThat(filteredListGenre.size).isEqualTo(2)
    }
}
