package com.example.project2bymiracle.presentation.photos

sealed class PhotosEvents {
    data class ShowSnackBar(val message: String): PhotosEvents()
}
