package com.example.project2bymiracle.domain.usecases

import com.example.project2bymiracle.domain.models.ValidationResult

class ValidateEmail {

    operator fun invoke(email: String): ValidationResult {

        return if (email.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Email cannot be empty!"
            )
        } else ValidationResult(
            successful = true
        )
    }
}