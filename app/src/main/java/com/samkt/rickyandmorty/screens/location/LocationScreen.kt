package com.samkt.rickyandmorty.screens.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.rickyandmorty.domain.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel,
) {
    val locations = viewModel.location.collectAsLazyPagingItems()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            LocationScreenContent(locations = locations)
        }
    }
}

@Composable
fun LocationScreenContent(
    locations: LazyPagingItems<Location>,
) {
    val count = locations.itemCount
    LazyColumn(
        content = {
            items(count) { index ->
                val location = locations[index]
                location?.let {
                    LocationCard(location = it)
                }
            }
        },
    )
}

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    location: Location,
) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF47aec4)),
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
        ) {
            Text(text = "Name : ${location.name}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Dimension: ${location.dimension}")
        }
    }
}
