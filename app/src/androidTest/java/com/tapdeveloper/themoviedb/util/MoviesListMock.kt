package com.tapdeveloper.themoviedb.util

import com.tapdeveloper.themoviedb.domain.model.Movie

object MoviesListMock {

    fun getMovies(): List<Movie> =
        listOf(Movie(id = 1, title = "title1"), Movie(id = 2, title = "title2"), Movie(id = 3, title = "title3"), Movie(id = 4, title = "title4"))

    fun getListSize(): Int =
        4
}