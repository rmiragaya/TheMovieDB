package com.tapdeveloper.themoviedb.data.repository

import com.tapdeveloper.themoviedb.data.db.MovieDatabase
import com.tapdeveloper.themoviedb.data.mappers.toEntity
import com.tapdeveloper.themoviedb.data.mappers.toMovie
import com.tapdeveloper.themoviedb.data.mappers.toMoviesListResponse
import com.tapdeveloper.themoviedb.data.remote.api.MovieApi
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.domain.repository.MovieRepository
import com.tapdeveloper.themoviedb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val api: MovieApi,
    private val db: MovieDatabase
) : MovieRepository {

    private val favoritesMovieDao = db.favoritesMoviesDao

    override suspend fun getMovies(page: Int?): Resource<MoviesListResponse> =
        try {
            Resource.Success(
                data = api.getMovies(page = page).toMoviesListResponse()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has ocurred")
        }

    override suspend fun searchMovies(query: String, page: Int?): Resource<MoviesListResponse> =
        try {
            Resource.Success(
                data = api.searchMovies(query = query, page = page).toMoviesListResponse()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has ocurred")
        }

    override suspend fun isMovieFavorites(movie: Movie): Resource<Boolean> =
        movie.id?.let { id ->
            withContext(Dispatchers.IO) {
                return@withContext try {
                    val isFavorited = favoritesMovieDao.getMovie(id)
                    if (isFavorited != null) {
                        Resource.Success(
                            data = true
                        )
                    } else {
                        Resource.Success(
                            data = false
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Resource.Error(e.message ?: "An unknown error has ocurred")
                }
            }
        } ?: run {
            return Resource.Error("No id for ${movie.title}")
        }

    override suspend fun getFavoritesMovies(): Resource<List<Movie>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Resource.Success(
                    data = favoritesMovieDao.getAllMovies().map { it.toMovie() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error(e.message ?: "An unknown error has ocurred")
            }
        }

    override suspend fun addMovieToFavorites(movie: Movie): Boolean {
        return withContext(Dispatchers.IO) {
            favoritesMovieDao.insertMovie(movie.toEntity()) >= 0
        }
    }

    override suspend fun removeMovieFromFavorites(movie: Movie) {
        withContext(Dispatchers.IO) {
            favoritesMovieDao.deleteMovie(movie.toEntity())
        }
    }
}
