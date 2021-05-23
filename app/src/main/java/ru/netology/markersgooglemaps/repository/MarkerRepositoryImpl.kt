package ru.netology.markersgooglemaps.repository

import androidx.lifecycle.*
import ru.netology.markersgooglemaps.dao.MarkersDao
import ru.netology.markersgooglemaps.dto.Marker
import ru.netology.markersgooglemaps.entity.MarkersEntity
import ru.netology.markersgooglemaps.entity.toDto
import ru.netology.markersgooglemaps.model.UnknownError

class MarkerRepositoryImpl(private val dao: MarkersDao) : MarkerRepository {

    override val data = dao.getAll().map(List<MarkersEntity>::toDto)

    override suspend fun saveMarker(marker: Marker) {
        try {
            dao.insert(MarkersEntity.fromDto(marker))
        } catch (e: Exception) {
            UnknownError
        }
    }

    override suspend fun deleteMarker(id: Int) {
        try {
            dao.deleteById(id)
        } catch (e: Exception) {
            UnknownError
        }
    }
}