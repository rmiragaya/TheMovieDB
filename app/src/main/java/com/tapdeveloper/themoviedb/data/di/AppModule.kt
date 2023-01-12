package com.tapdeveloper.themoviedb.data.di

import android.app.Application
import androidx.room.Room
import com.tapdeveloper.themoviedb.BuildConfig
import com.tapdeveloper.themoviedb.data.db.MovieDatabase
import com.tapdeveloper.themoviedb.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
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
