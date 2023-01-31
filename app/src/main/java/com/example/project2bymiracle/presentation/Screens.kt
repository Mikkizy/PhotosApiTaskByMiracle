package com.example.project2bymiracle.presentation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
    object PhotosScreen: Screen("photos")
}