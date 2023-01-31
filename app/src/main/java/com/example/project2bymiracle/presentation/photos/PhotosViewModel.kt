package com.example.project2bymiracle.presentation.photos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.domain.usecases.GetPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotos: GetPhotos
): ViewModel() {

    private val _state = mutableStateOf(PhotosState())
    val state: State<PhotosState> = _state

    private val _eventFlow = MutableSharedFlow<PhotosEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            getPhotos().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isError = true
                        )
                        _eventFlow.emit(PhotosEvents.ShowSnackBar(result.message ?: "An error occurred!"))
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            photos = result.data ?: emptyList(),
                            isLoading = false,
                            isError = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }













}