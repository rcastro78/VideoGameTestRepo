package sv.com.videogametest

import androidx.lifecycle.Observer

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.awaitResponse
import sv.com.videogamestest.model.VideoGameItem
import sv.com.videogamestest.networking.IVideoGame
import sv.com.videogamestest.viewmodel.VideoGameViewModel
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class VideoGameViewModelTest {

    private lateinit var videoGameViewModel: VideoGameViewModel
    private val mockVideoGameService: IVideoGame = mockk() // Mock para el servicio
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Usar un dispatcher de prueba

        // Inicializar el ViewModel con el mock del servicio
        videoGameViewModel = VideoGameViewModel(mockVideoGameService)
    }

    @Test
    fun `test getVideoGamesList success`() = runTest {
        val mockVideoGames = listOf(
            VideoGameItem(id = 1, title = "Game 1", genre = "Genre 1", thumbnail = "thumb1", short_description = "desc1", game_url = "url1", freetogame_profile_url = "url2", developer = "dev1", publisher = "pub1", platform = "plat1", release_date = "date1"),
            VideoGameItem(id = 2, title = "Game 2", genre = "Genre 2", thumbnail = "thumb2", short_description = "desc2", game_url = "url3", freetogame_profile_url = "url4", developer = "dev2", publisher = "pub2", platform = "plat2", release_date = "date2")
        )

        // Configura el mock del servicio para devolver una respuesta exitosa
        coEvery { mockVideoGameService.getVideoGames().awaitResponse() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockVideoGames
        }

        // Observa los cambios en el ViewModel
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        videoGameViewModel.isLoading

        // Llama a la función en el ViewModel
        videoGameViewModel.getVideoGamesList(
            OnSuccess = { videoGames ->
                assertTrue(videoGames.isNotEmpty())  // Verifica que la lista de juegos no esté vacía
            },
            OnError = {}
        )

        // Avanza el tiempo de las corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Verifica que isLoading se haya actualizado correctamente
        assertFalse(videoGameViewModel.isLoading) // Debería estar en false después del éxito

        // Verifica que el observer haya sido llamado
        verify { observer.onChanged(true) }
    }

    @Test
    fun `test getVideoGamesList failure`() = runTest {
        // Configura el mock para devolver un error
        coEvery { mockVideoGameService.getVideoGames().awaitResponse() } returns mockk {
            every { isSuccessful } returns false
        }

        // Observa los cambios en el ViewModel
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        videoGameViewModel.isLoading

        // Llama a la función en el ViewModel
        videoGameViewModel.getVideoGamesList(
            OnSuccess = {},
            OnError = {}
        )

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(videoGameViewModel.isLoading)

        verify { observer.onChanged(true) }
    }

    @After
    fun tearDown() {
        // Restablecer el dispatcher principal al final de las pruebas
        Dispatchers.resetMain()
    }
}