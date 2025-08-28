package com.vanlumban.nasaphotos.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlumban.nasaphotos.data.models.NasaPhoto
import com.vanlumban.nasaphotos.data.repository.NasaRepository
import com.vanlumban.nasaphotos.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<NasaPhoto>>>(UiState.Loading)
    val state: StateFlow<UiState<List<NasaPhoto>>> = _state

    init {
        fetchRandomPhotos()
    }

    fun fetchRandomPhotos() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val photo = repository.getRandomPhotos()
                _state.value = UiState.Success(photo)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
