package sv.com.videogamestest.networking

class VideoGameAPI {
    companion object {
        private const val WEBSERVICE_URL = "https://www.freetogame.com/"
        fun getVideoGameService(): IVideoGame? {
            return RetrofitClient.getClient(WEBSERVICE_URL)?.create(IVideoGame::class.java)
        }
    }
}