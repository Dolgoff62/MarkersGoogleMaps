package ru.netology.markersgooglemaps.utils

import ru.netology.markersgooglemaps.dto.Marker

class Utils {
    object EmptyMarker {
        val empty = Marker(
            id = 0,
            markerTitle = "",
            markerDescription = "",
            publishedDate = "",
            latitude = 0.0,
            longitude = 0.0
        )
    }
}
