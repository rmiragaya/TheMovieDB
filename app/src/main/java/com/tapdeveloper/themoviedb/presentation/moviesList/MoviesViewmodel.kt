package com.tapdeveloper.themoviedb.presentation.moviesList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapdeveloper.themoviedb.data.remote.api.DefaultPaginator
import com.tapdeveloper.themoviedb.domain.model.Movie
import com.tapdeveloper.themoviedb.domain.model.MoviesListResponse
import com.tapdeveloper.themoviedb.domain.repository.MovieRepository
import com.tapdeveloper.themoviedb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewmodel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var moviesResponse by mutableStateOf(MoviesListResponse())
    var selectedMovie by mutableStateOf(Movie())
    var isLoadingColum by mutableStateOf(false)

    var columnError by mutableStateOf<String?>(null)

    var endReached by mutableStateOf(false)
    private var pageKey: Int? = 1

    /** Observables for favorites*/
    var favoritesMovies by mutableStateOf(listOf<Movie>())
    var isLoadingRow by mutableStateOf(false)
    var rowError by mutableStateOf<String?>(null)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        columnError = throwable.message
    }

    private val paginator = DefaultPaginator(
        initialKey = pageKey,
        onLoadUpdated = {
            isLoadingColum = it
        },
        onRequest = { nextKey ->
            getMovies(nextKey)
        },
        getNextKey = {
            pageKey?.plus(1)
        },
        onError = {
            columnError = it?.message
        },
        onSuccess = { items, nextKey ->
            moviesResponse = MoviesListResponse(movies = moviesResponse.movies.plus(items))
            pageKey = nextKey
        }
    )

    private suspend fun getMovies(page: Int?): Result<List<Movie>> {
        isLoadingColum = true

        when (val result = repository.getMovies(
            page
        )) {
            is Resource.Success -> {
                isLoadingColum = false
                result.data?.movies?.let {
                    return Result.success(it)
                } ?: return Result.failure( Exception(result.message) )
            }
            is Resource.Error -> {
                isLoadingColum = false
                columnError = result.message
                return Result.failure(Exception(result.message))
            }
        }
    }

    fun selectMovie(movie: Movie) {
        selectedMovie = movie
    }

    fun loadNextMovies() {
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            paginator.loadNextItems()
        }
    }

    fun shouldFetchMoreMovies(actualIndex: Int): Boolean =
        (actualIndex >= moviesResponse.movies.lastIndex && !endReached && !isLoadingColum)
}