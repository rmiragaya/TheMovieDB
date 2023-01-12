package com.tapdeveloper.themoviedb.data.remote.api

import com.tapdeveloper.themoviedb.data.remote.dtos.MoviesListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int? = null
    ): MoviesListResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int? = null,
        @Query("query") query: String
    ): MoviesListResponseDto
}
