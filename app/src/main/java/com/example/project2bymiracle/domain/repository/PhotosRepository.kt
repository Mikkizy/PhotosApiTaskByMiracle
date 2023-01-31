package com.example.project2bymiracle.domain.repository

import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.domain.models.Photos
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    fun getPhotos(): Flow<Resource<List<Photos>>>
}