package com.lord_ukaka.projectbymiracle.presentation.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.project2bymiracle.R
import com.example.project2bymiracle.presentation.Screen
import com.example.project2bymiracle.presentation.photos.PhotosEvents
import com.example.project2bymiracle.presentation.photos.PhotosViewModel
import com.example.project2bymiracle.presentation.photos.components.ListContent
import com.example.project2bymiracle.presentation.photos.components.PhotosTopBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state.value
    val photosList = state.photos

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is PhotosEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PhotosTopBar {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            ListContent(items = photosList)
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.isError) {
                Column {
                    Text(
                        text = stringResource(id = R.string.error),
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.loadPhotos() },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.kinetic_color),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}