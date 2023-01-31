package com.example.project2bymiracle.domain.models

import com.example.project2bymiracle.domain.usecases.ValidateEmail
import com.example.project2bymiracle.domain.usecases.ValidatePassword

data class Validations(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword
)
