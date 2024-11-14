package sv.com.videogametest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class VideoGamesScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigateToDetailActivityOnClick() {
        composeTestRule.setContent {
            VideoGamesActivity().VideoGamesScreen()
        }
        composeTestRule.onNodeWithText("Game1").performClick()
    }
}
