package com.tapdeveloper.themoviedb.data.di

import android.app.Application
import androidx.room.Room
import com.tapdeveloper.themoviedb.BuildConfig
import com.tapdeveloper.themoviedb.data.db.MovieDatabase
import com.tapdeveloper.themoviedb.data.remote.api.MovieApi
import com.tapdeveloper.themoviedb.data.repository.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val keyInterceptor = ApiKeyInterceptor(BuildConfig.API_KEY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(keyInterceptor)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesVendorDatabase(
        app: Application
    ): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviesdb"
        ).build()
    }
}
