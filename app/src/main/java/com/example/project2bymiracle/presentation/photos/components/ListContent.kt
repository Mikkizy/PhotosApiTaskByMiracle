package com.example.project2bymiracle.presentation.photos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.project2bymiracle.common.Dimensions
import com.example.project2bymiracle.domain.models.Photos

@Composable
fun ListContent(items: List<Photos>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimensions.pagePadding.minus(8.dp)),
        verticalArrangement = Arrangement.spacedBy(Dimensions.oneSpacer.times(0.8f))
    ) {
        items(items = items) { albumInfo ->
            PhotoItem(photo = albumInfo)
        }
    }
}

@Composable
fun PhotoItem(photo: Photos) {
    val painter = rememberAsyncImagePainter(model = photo.thumbnailUrl)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.3f))
            .shadow(shape = MaterialTheme.shapes.medium, elevation = 1.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .shadow(shape = MaterialTheme.shapes.medium, elevation = 1.dp)
                .size(80.dp, 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = "Album Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.pagePadding.minus(10.dp))
        ) {
            Text(
                text = photo.title,
                modifier = Modifier
                    .align(Alignment.Center),
                style = MaterialTheme.typography.body2
            )
        }
    }
}