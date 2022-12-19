package com.tapdeveloper.themoviedb.data.mappers

import com.tapdeveloper.themoviedb.data.remote.dtos.MoviesListResponseDto
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse

fun MoviesListResponseDto.toMoviesListResponse(): MoviesListResponse =
    MoviesListResponse(
        movies = results?.map { it.toMovie() } ?: mutableListOf()
    )