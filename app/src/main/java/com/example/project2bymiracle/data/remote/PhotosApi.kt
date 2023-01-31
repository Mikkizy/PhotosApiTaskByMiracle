package com.example.project2bymiracle.data.remote

import com.example.project2bymiracle.domain.models.Photos
import retrofit2.Response
import retrofit2.http.GET

interface PhotosApi {

    @GET("/photos")
    suspend fun getPhotos() : Response<List<Photos>>
}