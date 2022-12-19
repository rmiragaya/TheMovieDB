package com.tapdeveloper.themoviedb.data.mappers

import com.tapdeveloper.themoviedb.data.remote.dtos.MovieDto
import com.tapdeveloper.themoviedb.domain.model.Movie

fun MovieDto.toMovie(): Movie =
    Movie(
        id = id,
        posterPath = poster_path,
        overview = overview,
        genreIds = genre_ids,
        title = title,
        releaseDate = release_date
    )

//fun SubscribedMovieEntity.toMovie(): Movie =
//    Movie(
//        id = id,
//        posterPath = posterPath,
//        overview = overview,
//        title = title,
//        releaseDate = releaseDate,
//        wasSubscribed = true
//    )
//
//fun Movie.toEntity(): SubscribedMovieEntity =
//    SubscribedMovieEntity(
//        id = id,
//        posterPath = posterPath,
//        overview = overview,
//        title = title,
//        releaseDate = releaseDate
//    )