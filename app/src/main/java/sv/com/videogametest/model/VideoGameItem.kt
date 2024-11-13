package sv.com.videogamestest.model

data class VideoGameItem(
    var developer: String,
    var freetogame_profile_url: String,
    var game_url: String,
    var genre: String,
    var id: Int,
    var platform: String,
    var publisher: String,
    var release_date: String,
    var short_description: String,
    var thumbnail: String,
    var title: String
)