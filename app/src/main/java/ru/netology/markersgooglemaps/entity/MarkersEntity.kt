package ru.netology.markersgooglemaps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.markersgooglemaps.dto.Marker

@Entity
data class MarkersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val markerTitle: String,
    val markerDescription: String,
    val publishedDate: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDto() = Marker(
        id,
        markerTitle,
        markerDescription,
        publishedDate,
        latitude,
        longitude
    )


    companion object {
        fun fromDto(dto: Marker) =
            MarkersEntity(
                dto.id,
                dto.markerTitle,
                dto.markerDescription,
                dto.publishedDate,
                dto.latitude,
                dto.longitude
            )
    }
}
fun List<MarkersEntity>.toDto(): List<Marker> = map(MarkersEntity::toDto)
