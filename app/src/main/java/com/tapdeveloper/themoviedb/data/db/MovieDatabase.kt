package com.tapdeveloper.themoviedb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tapdeveloper.themoviedb.data.db.daos.FavoritesMovieDao
import com.tapdeveloper.themoviedb.data.db.entities.FavoritesMovieEntity

@Database(
    entities = [
        FavoritesMovieEntity::class
    ],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val favoritesMoviesDao: FavoritesMovieDao
}