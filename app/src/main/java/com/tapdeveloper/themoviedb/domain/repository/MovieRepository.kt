package com.tapdeveloper.themoviedb.domain.repository

import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.util.Resource

interface MovieRepository {

    suspend fun getMovies(
        page: Int?
    ): Resource<MoviesListResponse>

    suspend fun getMovieDetail(
        movieId: Long
    ): Resource<Movie>

    suspend fun searchMovies(
        query: String,
        page: Int?
    ): Resource<MoviesListResponse>

    suspend fun isMovieSubscribed(movie: Movie): Resource<Boolean>

    suspend fun getSubscribedMovies(): Resource<List<Movie>>

    suspend fun subscribeMovie(movie: Movie): Boolean

    suspend fun unsubscribeMovie(movie: Movie)

}