package com.example.project2bymiracle.domain.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
