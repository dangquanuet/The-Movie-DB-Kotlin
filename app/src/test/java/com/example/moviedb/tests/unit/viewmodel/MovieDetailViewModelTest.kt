package com.example.moviedb.tests.unit.viewmodel

import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.tests.support.MainDispatcherExtension
import com.example.moviedb.tests.support.TestData
import com.example.moviedb.ui.base.ErrorType
import com.example.moviedb.ui.screen.moviedetail.MovieDetailViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.UnknownHostException

@ExtendWith(MainDispatcherExtension::class)
@Epic("Movie DB")
@Feature("MovieDetailViewModel")
class MovieDetailViewModelTest {

    private val userRepository = mockk<UserRepository>(relaxed = true)
    private lateinit var viewModel: MovieDetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieDetailViewModel(userRepository)
    }

    @Test
    @Story("Load cast and crew")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("loadCastAndCrew заполняет castList данными из репозитория")
    fun loadCastAndCrewPopulatesList() {
        val cast = listOf(TestData.cast("Christian Bale"), TestData.cast("Heath Ledger"))
        coEvery { userRepository.getCastAndCrew("1") } returns TestData.castResponse(*cast.toTypedArray())

        viewModel.loadCastAndCrew("1")

        assertEquals(cast, viewModel.castList.value)
        coVerify(exactly = 1) { userRepository.getCastAndCrew("1") }
    }

    @Test
    @Story("Load cast and crew")
    @DisplayName("loadCastAndCrew не повторяет запрос, если список уже загружен")
    fun loadCastAndCrewSkipsWhenAlreadyLoaded() {
        coEvery { userRepository.getCastAndCrew("1") } returns TestData.castResponse(TestData.cast())

        viewModel.loadCastAndCrew("1")
        viewModel.loadCastAndCrew("1")

        coVerify(exactly = 1) { userRepository.getCastAndCrew("1") }
    }

    @Test
    @Story("Load cast and crew")
    @DisplayName("loadCastAndCrew при ошибке сети выставляет ErrorType.NoInternetConnection")
    fun loadCastAndCrewSetsErrorOnFailure() {
        coEvery { userRepository.getCastAndCrew(any()) } throws UnknownHostException()

        viewModel.loadCastAndCrew("1")

        assertEquals(ErrorType.NoInternetConnection, viewModel.uiState.value.errorType)
    }

    @Test
    @Story("Favorite")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("favoriteMovie переключает isFavorite и сохраняет через updateDB")
    fun favoriteMovieTogglesAndPersists() {
        viewModel.movie.value = TestData.movie(isFavorite = false)

        viewModel.favoriteMovie()

        assertEquals(true, viewModel.movie.value?.isFavorite)
        coVerify(exactly = 1) { userRepository.updateDB(any()) }
    }

    @Test
    @Story("Favorite")
    @DisplayName("checkFavorite проставляет избранное по данным локального хранилища")
    fun checkFavoriteMarksMovieFromLocal() {
        viewModel.movie.value = TestData.movie(isFavorite = false)
        coEvery { userRepository.getMovieLocal("1") } returns TestData.movie(isFavorite = true)

        viewModel.checkFavorite("1")

        assertTrue(viewModel.movie.value?.isFavorite == true)
        coVerify(exactly = 1) { userRepository.getMovieLocal("1") }
    }
}
