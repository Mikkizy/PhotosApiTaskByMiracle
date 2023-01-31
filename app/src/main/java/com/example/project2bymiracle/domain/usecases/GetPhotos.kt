package com.example.project2bymiracle.domain.usecases

import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.domain.models.Photos
import com.example.project2bymiracle.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotos @Inject constructor(
    private val repository: PhotosRepository
) {

    operator fun invoke(): Flow<Resource<List<Photos>>> {
        return repository.getPhotos()
    }
}