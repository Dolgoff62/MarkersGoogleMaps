package ru.netology.markersgooglemaps.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.markersgooglemaps.entity.MarkersEntity

@Dao
interface MarkersDao {

    @Query("SELECT * FROM MarkersEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<MarkersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marker: MarkersEntity)

    @Query("DELETE FROM MarkersEntity WHERE id = :id")
    suspend fun deleteById(id: Int)
}
