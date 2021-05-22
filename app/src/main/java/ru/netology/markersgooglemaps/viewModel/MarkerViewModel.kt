package ru.netology.markersgooglemaps.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.markersgooglemaps.db.AppDb
import ru.netology.markersgooglemaps.dto.Marker
import ru.netology.markersgooglemaps.model.FeedModel
import ru.netology.markersgooglemaps.repository.MarkerRepository
import ru.netology.markersgooglemaps.repository.MarkerRepositoryImpl
import ru.netology.markersgooglemaps.utils.Utils
import ru.netology.markersgooglemaps.utils.Utils.EmptyMarker.empty

class MarkerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MarkerRepository =
        MarkerRepositoryImpl(AppDb.getInstance(context = application).markersDao())

    val data: LiveData<FeedModel> = repository.data.map(::FeedModel)

    private val _dataState = MutableLiveData<FeedModel>()
    val dataState: LiveData<FeedModel>
        get() = _dataState

    val edited = MutableLiveData(empty)

    init {
        loadMarkers()
    }

    fun loadMarkers() = viewModelScope.launch {
        try {
            _dataState.value = FeedModel(refreshing = true)
            repository.data
            _dataState.value = FeedModel()
        } catch (e: Exception) {
            _dataState.value = FeedModel(error = true)
        }
    }

    fun saveMarker() {
        edited.value?.let {
            viewModelScope.launch {
                try {
                    _dataState.value = FeedModel(loading = true)
                    repository.saveMarker(it)
                    _dataState.value = FeedModel()
                } catch (e: Exception) {
                    _dataState.value = FeedModel(error = true)
                }
            }
        }
        edited.value = empty
    }

    fun updateMarker(marker: Marker) {
        edited.value = marker
    }

    fun changeContent(
        id: Int,
        markerTitle: String,
        markerDescription: String,
        externalLatitude: Double,
        externalLongitude: Double
    ) {
        val title = markerTitle.trim()
        val description = markerDescription.trim()
        if (
            edited.value?.markerTitle == title
            && edited.value?.markerDescription == description
        ) {
            return
        }
        edited.value = edited.value?.copy(
            id = id,
            markerTitle = title,
            markerDescription = description,
            publishedDate = Utils.localDateTime(),
            latitude = externalLatitude,
            longitude = externalLongitude
        )
    }
}