package com.example.moviedb.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviedb.R
import com.example.moviedb.compose.toMovieDetail
import com.example.moviedb.data.model.Movie
import com.example.moviedb.ui.screen.popularmovie.PopularMovieViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: PopularMovieViewModel = hiltViewModel()
) {
    val itemListUiState by viewModel.itemsUiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(itemListUiState.isRefreshing, { viewModel.doRefresh() })
    val scrollState = rememberLazyGridState()
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }
    LaunchedEffect(key1 = true, block = {
        viewModel.firstLoad()
    })

    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
        ) {
            items(
                items = itemListUiState.items,
                key = { movie: Movie -> movie.id },
                itemContent = { movie ->
                    ItemMovie(
                        movie = movie,
                        onClick = {
                            navController.toMovieDetail(movieId = it.id)
                        },
                    )
                }
            )
        }

        PullRefreshIndicator(
            refreshing = itemListUiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    LaunchedEffect(key1 = endOfListReached, block = {
        viewModel.doLoadMore()
    })
}

fun LazyGridState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 5

@Composable
fun ItemMovie(movie: Movie, onClick: (Movie) -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onClick.invoke(movie)
            }
            .fillMaxWidth()
    ) {
        GlideImage(
            imageModel = { movie.getFullPosterPath() ?: "" },
            modifier = Modifier.fillMaxWidth(),
            component = rememberImageComponent {
                +PlaceholderPlugin.Loading(Icons.Filled.Image)
                +PlaceholderPlugin.Failure(Icons.Filled.Error)
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
            ),
        )

        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.black_50))
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = movie.title ?: "",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}