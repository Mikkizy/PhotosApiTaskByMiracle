package com.example.project2bymiracle.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Photos(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)
