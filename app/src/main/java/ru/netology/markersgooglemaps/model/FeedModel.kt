package ru.netology.markersgooglemaps.model

import ru.netology.markersgooglemaps.dto.Marker

data class FeedModel(
    val markers: List<Marker> = emptyList(),
    val empty: Boolean = false,
    val refreshing: Boolean,
    val error: Boolean = false
)
