package com.example.project2bymiracle.dependency_injection

import com.example.project2bymiracle.common.Constants.BASE_URL
import com.example.project2bymiracle.data.remote.PhotosApi
import com.example.project2bymiracle.data.repository.PhotosRepositoryImpl
import com.example.project2bymiracle.domain.models.Validations
import com.example.project2bymiracle.domain.repository.PhotosRepository
import com.example.project2bymiracle.domain.usecases.GetPhotos
import com.example.project2bymiracle.domain.usecases.ValidateEmail
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.project2bymiracle.domain.usecases.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providePhotosApi(): PhotosApi {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PhotosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetAlbumsUseCase(repository: PhotosRepository): GetPhotos {
        return GetPhotos(repository)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(
        api: PhotosApi
    ): PhotosRepository {
        return PhotosRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideValidations(): Validations {
        return Validations(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword()
        )
    }
}