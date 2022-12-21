package com.tapdeveloper.themoviedb.domain.repository

import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.util.Resource

interface MovieRepository {

    suspend fun getMovies(
        page: Int?
    ): Resource<MoviesListResponse>

    suspend fun searchMovies(
        query: String,
        page: Int?
    ): Resource<MoviesListResponse>

    suspend fun isMovieFavorites(movie: Movie): Resource<Boolean>

    suspend fun getFavoritesMovies(): Resource<List<Movie>>

    suspend fun addMovieToFavorites(movie: Movie): Boolean

    suspend fun removeMovieFromFavorites(movie: Movie)

}