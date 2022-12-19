package com.tapdeveloper.themoviedb.data.repository

import com.tapdeveloper.themoviedb.data.mappers.toMoviesListResponse
import com.tapdeveloper.themoviedb.data.remote.api.MovieApi
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.domain.repository.MovieRepository
import com.tapdeveloper.themoviedb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override suspend fun getMovies(page: Int?): Resource<MoviesListResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Resource.Success(
                    data = api.getMovies(page = page).toMoviesListResponse()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.message ?: "An unknown error has ocurred")
            }
        }

    override suspend fun getMovieDetail(movieId: Long): Resource<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String, page: Int?): Resource<MoviesListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun isMovieSubscribed(movie: Movie): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscribedMovies(): Resource<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeMovie(movie: Movie): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun unsubscribeMovie(movie: Movie) {
        TODO("Not yet implemented")
    }
}