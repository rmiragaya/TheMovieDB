package com.tapdeveloper.themoviedb.domain.model

data class MoviesListResponse(
    val page: Int? = null,
    val movies: List<Movie> = mutableListOf()
)
