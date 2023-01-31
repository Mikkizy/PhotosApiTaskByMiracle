package com.example.project2bymiracle.domain.usecases

import com.example.project2bymiracle.domain.models.ValidationResult

class ValidatePassword {

    operator fun invoke(password: String): ValidationResult {

        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password cannot be empty!"
            )
        } else return ValidationResult(
            successful = true
        )
    }
}