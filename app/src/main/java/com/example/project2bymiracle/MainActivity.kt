package com.example.project2bymiracle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project2bymiracle.presentation.Screen
import com.example.project2bymiracle.presentation.login.LoginScreen
import com.example.project2bymiracle.ui.theme.Project2ByMiracleTheme
import com.lord_ukaka.projectbymiracle.presentation.photos.PhotosScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project2ByMiracleTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Screen.PhotosScreen.route) {
                        PhotosScreen(navController = navController)
                    }
                }
            }
        }
    }
}