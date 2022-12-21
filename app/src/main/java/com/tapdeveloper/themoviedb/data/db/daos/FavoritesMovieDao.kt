package com.tapdeveloper.themoviedb.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tapdeveloper.themoviedb.data.db.entities.FavoritesMovieEntity

@Dao
interface FavoritesMovieDao {

    @Insert
    suspend fun insertMovie(movie: FavoritesMovieEntity): Long

    @Delete
    suspend fun deleteMovie(movie: FavoritesMovieEntity)

    @Query("SELECT * FROM favorites_movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Long): FavoritesMovieEntity?

    @Query("SELECT * FROM favorites_movies")
    suspend fun getAllMovies(): List<FavoritesMovieEntity>
}