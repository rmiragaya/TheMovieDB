package com.tapdeveloper.themoviedb.data.remote.dtos

data class MovieDto(
    val id: Long? = null,
    val poster_path: String? = null,
    val overview: String? = null,
    val genre_ids: List<Int>? = null,
    val title: String? = null,
    val release_date: String? = null
)