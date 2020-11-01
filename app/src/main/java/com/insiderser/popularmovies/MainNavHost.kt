package com.insiderser.popularmovies

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import com.insiderser.popularmovies.ui.MovieDetailsScreen
import com.insiderser.popularmovies.ui.MoviesScreen
import com.insiderser.popularmovies.ui.PopularMoviesTheme

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "movies") {
        composable("movies") {
            MoviesScreen(onMovieClick = { movie -> navController.navigate("movies/${movie.id}") })
        }
        composable("movies/{movieId}") { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt("movieId"))
            MovieDetailsScreen(movieId = movieId)
        }
    }
}

@Preview
@Composable
fun MainNavigationPreview() {
    PopularMoviesTheme {
        MainNavHost()
    }
}
