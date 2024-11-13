package sv.com.videogamestest.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(
    tableName = VideoGameEntity.TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)]  // Hace que id sea único
)
class VideoGameEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "id")  val  id:Int,
    @ColumnInfo(name = "title")  val  title:String,
    @ColumnInfo(name = "freetogame_profile_url")  val  freetogame_profile_url:String,
    @ColumnInfo(name = "game_url")  val  game_url:String,
    @ColumnInfo(name = "genre")  val  genre:String,
    @ColumnInfo(name = "thumbnail")  val  thumbnail:String,
    @ColumnInfo(name = "short_description")  val  short_description:String,
    @ColumnInfo(name = "platform")  val  platform:String,
    @ColumnInfo(name = "publisher")  val  publisher:String,
    @ColumnInfo(name = "developer")  val  developer:String,
    @ColumnInfo(name = "release_date")  val  release_date:String,
    @ColumnInfo(name = "status")  val  status:Int?=1
){

    companion object {
        const val TABLE_NAME = "VideoGame"
    }

    @Dao
    interface IVideoGameDAO {
        @Query("SELECT * FROM $TABLE_NAME WHERE status = 1")
        fun getAll(): List<VideoGameEntity>

        @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
        fun get(id: Int): VideoGameEntity

        @Query("SELECT * FROM $TABLE_NAME WHERE status = 1")
        fun getActive(): List<VideoGameEntity>

        @Query("SELECT * FROM $TABLE_NAME WHERE status = 1")
        fun getDetail(): List<VideoGameEntity>

        @Query("SELECT * FROM $TABLE_NAME WHERE status = 0")
        fun getInactive(): List<VideoGameEntity>

        @Query("UPDATE $TABLE_NAME SET status = 0 WHERE id = :id")
        fun delete(id: Int)

        @Query("UPDATE $TABLE_NAME SET status = 1 WHERE uid = :uid")
        fun restore(uid: Long)

        @Query("UPDATE $TABLE_NAME SET status = 0 WHERE status = 1")
        fun deleteAll()

        @Query("UPDATE $TABLE_NAME SET status = 1 WHERE status = 0")
        fun restoreAll()

        @Query("UPDATE $TABLE_NAME SET short_description = :short_description WHERE id = :id")
        fun updateShortDescription(short_description:String, id: Int)

        @Insert
        fun insert(videoGameDAO: VideoGameEntity)

        @Update
        fun update(videoGameDAO: VideoGameEntity)



    }

}