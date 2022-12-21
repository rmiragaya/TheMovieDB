package com.tapdeveloper.themoviedb.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_movies")
class FavoritesMovieEntity(
    @PrimaryKey
    val id: Long? = null,
    val posterPath: String? = null,
    val overview: String? = null,
    val title: String? = null,
    val releaseDate: String? = null
)