package ru.netology.markersgooglemaps.repository

import androidx.lifecycle.LiveData
import ru.netology.markersgooglemaps.dto.Marker

interface MarkerRepository {
    val data: LiveData<List<Marker>>
    suspend fun saveMarker(marker: Marker)
    suspend fun updateMarker(marker: Marker)
    suspend fun deleteMarker(id: Int)
}
