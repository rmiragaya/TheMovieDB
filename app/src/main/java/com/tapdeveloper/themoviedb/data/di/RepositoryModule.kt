package com.tapdeveloper.themoviedb.data.di

import com.tapdeveloper.themoviedb.data.repository.MovieRepoImpl
import com.tapdeveloper.themoviedb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepoImpl
    ): MovieRepository
}
