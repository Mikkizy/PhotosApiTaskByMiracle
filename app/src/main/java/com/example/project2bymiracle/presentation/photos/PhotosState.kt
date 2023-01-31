package com.example.project2bymiracle.presentation.photos

import com.example.project2bymiracle.domain.models.Photos

data class PhotosState(
    val photos: List<Photos> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)