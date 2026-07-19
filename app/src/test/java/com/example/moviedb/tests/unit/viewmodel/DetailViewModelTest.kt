package com.example.moviedb.tests.unit.viewmodel

import com.example.moviedb.compose.ui.base.ErrorEvent
import com.example.moviedb.compose.ui.detail.DetailViewModel
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.tests.support.MainDispatcherExtension
import com.example.moviedb.tests.support.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.UnknownHostException

@ExtendWith(MainDispatcherExtension::class)
@Epic("Movie DB")
@Feature("Compose DetailViewModel")
class DetailViewModelTest {

    private val userRepository = mockk<UserRepository>(relaxed = true)
    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailViewModel(userRepository)
    }

    @Test
    @Story("Load movie detail")
    @DisplayName("getMovieDetail для null/пустого id не обращается к репозиторию")
    fun getMovieDetailIgnoresBlankId() {
        viewModel.getMovieDetail(null)
        viewModel.getMovieDetail("   ")

        assertNull(viewModel.movie.value)
        coVerify(exactly = 0) { userRepository.getMovieById(any()) }
    }

    @Test
    @Story("Load movie detail")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("getMovieDetail загружает фильм и выключает loading")
    fun getMovieDetailLoadsMovie() {
        coEvery { userRepository.getMovieById("10") } returns TestData.movie(id = "10", title = "The Dark Knight")

        viewModel.getMovieDetail("10")

        assertEquals("10", viewModel.movie.value?.id)
        assertEquals("The Dark Knight", viewModel.movie.value?.title)
        assertFalse(viewModel.loading.value)
        coVerify(exactly = 1) { userRepository.getMovieById("10") }
    }

    @Test
    @Story("Load movie detail")
    @DisplayName("getMovieDetail при ошибке сети эмитит ErrorEvent.Network и снимает loading")
    fun getMovieDetailEmitsNetworkError() {
        coEvery { userRepository.getMovieById(any()) } throws UnknownHostException()

        viewModel.getMovieDetail("10")

        assertEquals(ErrorEvent.Network, viewModel.errorEvent.value)
        assertFalse(viewModel.loading.value)
    }

    @Test
    @Story("Refresh")
    @DisplayName("doRefresh перезагружает фильм по сохранённому id и снимает refreshing")
    fun doRefreshReloadsMovie() {
        viewModel.setValueMovieId("10")
        coEvery { userRepository.getMovieById("10") } returns TestData.movie(id = "10", title = "Inception")

        viewModel.doRefresh()

        assertEquals("Inception", viewModel.movie.value?.title)
        assertFalse(viewModel.refreshing.value)
        coVerify(exactly = 1) { userRepository.getMovieById("10") }
    }

    @Test
    @Story("Refresh")
    @DisplayName("doRefresh без установленного id не обращается к репозиторию")
    fun doRefreshDoesNothingWithoutId() {
        viewModel.doRefresh()

        coVerify(exactly = 0) { userRepository.getMovieById(any()) }
    }
}
