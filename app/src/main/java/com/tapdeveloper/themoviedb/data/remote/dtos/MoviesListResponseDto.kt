package com.tapdeveloper.themoviedb.data.remote.dtos

data class MoviesListResponseDto(
    val page: Int? = null,
    val results: List<MovieDto>? = null
)
