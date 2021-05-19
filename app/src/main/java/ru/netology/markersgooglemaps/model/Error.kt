package ru.netology.markersgooglemaps.model

sealed class Error(var code: String): RuntimeException()

object UnknownError: Error("error_unknown")
