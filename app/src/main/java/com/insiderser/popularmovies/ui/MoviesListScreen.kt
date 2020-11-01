package com.insiderser.popularmovies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.insiderser.popularmovies.di.AppComponent
import com.insiderser.popularmovies.model.Movie
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun MoviesScreen(
    onMovieClick: (Movie) -> Unit
) {
    val viewModel = viewModel<MoviesListViewModel>(factory = AppComponent.get().viewModelFactory)
    val moviesList by viewModel.movies.collectAsState()

    Scaffold {
        MoviesList(
            moviesList,
            onItemClick = onMovieClick
        )
    }
}

@Composable
fun MoviesList(
    list: List<Movie>,
    onItemClick: (Movie) -> Unit
) {
    LazyColumnFor(
        items = list
    ) { movie ->
        MovieItem(
            movie = movie,
            modifier = Modifier
                .padding(16.dp)
                // FIXME: this looks dirty. Why should list adapter clip its children?
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = { onItemClick(movie) })
        )
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    CoilImage(
        data = movie.imageUrl,
        modifier = modifier
            .aspectRatio(16f / 9)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun MoviesListPreview() {
    PopularMoviesTheme {
        MoviesList(
            list = listOf(
                Movie(
                    id = 1,
                    title = "Movie 1",
                    description = "",
                    imageUrl = "https://images.unsplash.com/photo-1528183087798-c83d848b0ecd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
                ),
                Movie(
                    id = 2,
                    title = "Movie 2",
                    description = "",
                    imageUrl = "https://images.unsplash.com/photo-1528183087798-c83d848b0ecd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
                )
            ),
            onItemClick = {}
        )
    }
}
