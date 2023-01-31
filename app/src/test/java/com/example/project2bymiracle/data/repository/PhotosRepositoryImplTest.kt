package com.example.project2bymiracle.data.repository

import com.example.project2bymiracle.common.Resource
import com.example.project2bymiracle.data.remote.PhotosApi
import com.example.project2bymiracle.domain.models.Photos
import com.example.project2bymiracle.domain.repository.PhotosRepository
import com.google.gson.Gson
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class PhotosRepositoryImplTest {
    private lateinit var photosRepository: PhotosRepository
    private lateinit var photosApi: PhotosApi
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)
        photosApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(PhotosApi::class.java)
        photosRepository = PhotosRepositoryImpl(photosApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `PhotosApi parses correctly`() = runTest{
        val photosList = listOf(
            Photos(1,1, "jdn", "peace", "js"),
            Photos(1,2, "shu", "love", "js"),
            Photos(1,3, "jdn", "joy", "js"),
            Photos(1,4, "jdn", "faith", "link")
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(photosList))
        mockWebServer.apply {
            enqueue(expectedResponse)
        }

        var actualResponse = emptyList<Photos>()
        photosRepository.getPhotos().collectLatest { result ->
            when(result) {
                is Resource.Success -> {
                     actualResponse = result.data!!
                }
                else -> {
                    emptyList<Photos>()
                }
            }
        }

        assertThat(actualResponse.count()).isEqualTo(4)
    }
}