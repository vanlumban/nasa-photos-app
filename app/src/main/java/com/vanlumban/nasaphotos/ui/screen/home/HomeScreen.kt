package com.vanlumban.nasaphotos.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vanlumban.nasaphotos.data.models.NasaPhoto
import com.vanlumban.nasaphotos.ui.components.ShimmerEffect
import com.vanlumban.nasaphotos.util.UiState
import com.vanlumban.nasaphotos.util.cleanOrDefault
import com.vanlumban.nasaphotos.util.formatDate
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val uiState = viewModel.state.collectAsState().value

    PullToRefreshBox(
        isRefreshing = uiState is UiState.Loading,
        onRefresh = { viewModel.fetchRandomPhotos() }
    ) {
        when (uiState) {
            is UiState.Loading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(5) {
                        ShimmerPhotoCardPlaceholder()
                    }
                }
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.data.size) { index ->
                        PhotoCard(navController, photo = uiState.data[index])
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Failed to load photos", style = MaterialTheme.typography.titleMedium, color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchRandomPhotos() }) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoCard(navController: NavController, photo: NasaPhoto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val json = Json { encodeDefaults = true; explicitNulls = true }
                val jsonString = json.encodeToString(NasaPhoto.serializer(), photo)
                val encoded = URLEncoder.encode(jsonString, "UTF-8")
                navController.navigate("details/$encoded")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image
            AsyncImage(
                model = photo.url,
                contentDescription = photo.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = photo.title.cleanOrDefault("Untitled"),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Author: ${photo.copyright?.cleanOrDefault() ?: "---"}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Date: ${photo.date?.formatDate() ?: "---"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ShimmerPhotoCardPlaceholder() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            // Image placeholder
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title placeholder
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Author placeholder
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Date placeholder
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                )
            }
        }
    }
}
