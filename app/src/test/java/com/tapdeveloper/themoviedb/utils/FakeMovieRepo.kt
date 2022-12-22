package com.tapdeveloper.themoviedb.utils

import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.domain.repository.MovieRepository
import com.tapdeveloper.themoviedb.util.Resource

class FakeMovieRepo : MovieRepository {

    private val subscribeMovies = mutableListOf<Movie>()
    private val movies = MoviesListMock.getMovies()

    override suspend fun getMovies(page: Int?): Resource<MoviesListResponse> {
        return Resource.Success(data = MoviesListResponse(movies = movies))
    }

    override suspend fun searchMovies(query: String, page: Int?): Resource<MoviesListResponse> {
        return Resource.Success(data = MoviesListResponse(movies = movies.filter { it.title!!.contains(query) }))
    }

    override suspend fun isMovieFavorites(movie: Movie): Resource<Boolean> {
        return Resource.Success(data = subscribeMovies.contains(movie))
    }

    override suspend fun getFavoritesMovies(): Resource<List<Movie>> {
        return Resource.Success(data = subscribeMovies)
    }

    override suspend fun addMovieToFavorites(movie: Movie): Boolean =
        subscribeMovies.add(movie)

    override suspend fun removeMovieFromFavorites(movie: Movie) {
        subscribeMovies.remove(movie)
    }
}
