package com.tapdeveloper.themoviedb.presentation.movielist

import com.google.common.truth.Truth.assertThat
import com.tapdeveloper.themoviedb.presentation.moviesList.MoviesViewmodel
import com.tapdeveloper.themoviedb.utils.FakeMovieRepo
import com.tapdeveloper.themoviedb.utils.MainDispatcherRule
import com.tapdeveloper.themoviedb.utils.MoviesListMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewmodelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MoviesViewmodel

    @Before
    fun setUp() {
        viewModel = MoviesViewmodel(FakeMovieRepo())
    }

    @Test
    fun whenLoadNextMovies_ResponseUpdate() {
        runTest {
            assertThat(viewModel.moviesResponse.movies).isEmpty()
            viewModel.loadNextMovies()
            advanceUntilIdle()
            assertThat(viewModel.moviesResponse.movies).containsExactlyElementsIn(MoviesListMock.getMovies())
        }
    }

    @Test
    fun shouldntFetchMoreWhenScrollOnTop() {
        runTest {
            viewModel.loadNextMovies()
            advanceUntilIdle()
            assertThat(viewModel.shouldFetchMoreMovies(0)).isFalse()
        }
    }

    @Test
    fun shouldFetchMoreWhenScrollOnBottom() {
        runTest {
            viewModel.loadNextMovies()
            advanceUntilIdle()
            assertThat(viewModel.shouldFetchMoreMovies(MoviesListMock.getListSize())).isTrue()
        }
    }

    @Test
    fun searchQueryShowResults() {
        runTest {
            val query = "title1"
            viewModel.loadNextMovies()
            advanceUntilIdle()
            assertThat(viewModel.moviesResponse.movies).containsExactlyElementsIn(MoviesListMock.getMovies())
            viewModel.searchMovies(query)
            advanceUntilIdle()
            assertThat(viewModel.moviesResponse.movies).containsExactlyElementsIn(
                MoviesListMock.getMovies().filter { it.title!!.contains(query) }
            )
        }
    }

    @Test
    fun whenSelectingAMovie_ThenTheMovieIsSelected() {
        val movie = MoviesListMock.getMovies()[0]
        viewModel.selectMovie(movie)
        assertThat(viewModel.selectedMovie).isEqualTo(movie)
    }

    @Test
    fun listEmptyWhenNoFavoritesMovies() {
        runTest {
            viewModel.getFavoritesMovies()
            advanceUntilIdle()
            assertThat(viewModel.favoritesMovies).isEmpty()
        }
    }

    @Test
    fun favoriteMovieShouldBeAddedToTheList() {
        runTest {
            assertThat(viewModel.favoritesMovies).isEmpty()
            val movie = MoviesListMock.getMovies().first()
            viewModel.selectMovie(movie)
            viewModel.addOrRemoveFavorites()
            viewModel.getFavoritesMovies()
            advanceUntilIdle()
            assertThat(viewModel.favoritesMovies).contains(movie)
        }
    }

    @Test
    fun removedFavoriteMovieShouldBeRemovedFromTheList() {
        runTest {
            assertThat(viewModel.favoritesMovies).isEmpty()
            val movie = MoviesListMock.getMovies().first()
            viewModel.selectMovie(movie)
            viewModel.addOrRemoveFavorites()
            viewModel.getFavoritesMovies()
            advanceUntilIdle()
            assertThat(viewModel.favoritesMovies).contains(movie)

            viewModel.addOrRemoveFavorites()
            advanceUntilIdle()
            assertThat(viewModel.favoritesMovies).isEmpty()
        }
    }
}
