package sv.com.videogamestest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoGameEntity::class], version = 1)
abstract class VideoGameDB : RoomDatabase() {
    abstract fun iVideoGameDAO(): VideoGameEntity.IVideoGameDAO

    companion object {
        @Volatile
        private var INSTANCE: VideoGameDB? = null

        fun getInstance(context: Context): VideoGameDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoGameDB::class.java,
                    "videogames_00.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}