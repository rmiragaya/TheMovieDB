package com.tapdeveloper.themoviedb.data.remote.api

import com.tapdeveloper.themoviedb.BuildConfig
import com.tapdeveloper.themoviedb.data.remote.dtos.MoviesListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int? = null
    ): MoviesListResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int? = null,
        @Query("query") query: String
    ): MoviesListResponseDto
}
