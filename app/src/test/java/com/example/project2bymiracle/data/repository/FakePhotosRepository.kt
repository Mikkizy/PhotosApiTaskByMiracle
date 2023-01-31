package com.example.project2bymiracle.data.repository

import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.domain.models.Photos
import com.example.project2bymiracle.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePhotosRepository: PhotosRepository {

    private val photos = mutableListOf<Photos>()

    private var shouldReturnNetworkError = false

    /**
     * Returns a boolean depending on the network state of the device.
     */
    private fun shouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getPhotos(): Flow<Resource<List<Photos>>> {
        return if (shouldReturnNetworkError) {
            flow {
                emit(Resource.Error("An error occurred!"))
            }
        } else {
            flow {
                emit(Resource.Success(listOf(Photos(1, 2, "", "", ""))))
                shouldReturnNetworkError(false)
            }
        }
    }
}