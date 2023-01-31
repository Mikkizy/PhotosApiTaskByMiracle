package com.example.project2bymiracle.data.repository

import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.data.remote.PhotosApi
import com.example.project2bymiracle.domain.models.Photos
import com.example.project2bymiracle.domain.repository.PhotosRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val api: PhotosApi
): PhotosRepository {
    override fun getPhotos(): Flow<Resource<List<Photos>>> = flow {
        emit(Resource.Loading())
        delay(2000L)
        try {
            val response = api.getPhotos()
            if (response.isSuccessful) {
                response.body()?.let { photos ->
                    return@let emit(Resource.Success(photos))
                } ?:  emit( Resource.Error("An unknown error occurred!"))
            } else {
                emit( Resource.Error("Couldn't fetch data, try again!"))
            }
        } catch (e: Exception) {
            emit( Resource.Error("An error occurred! Check Internet Connection"))
        }
    }
}