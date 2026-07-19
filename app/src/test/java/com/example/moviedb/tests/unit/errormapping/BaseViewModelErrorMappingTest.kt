package com.example.moviedb.tests.unit.errormapping

import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.ui.base.ErrorType
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Epic("Movie DB")
@Feature("BaseViewModel: error mapping")
class BaseViewModelErrorMappingTest {

    private class TestableBaseViewModel : BaseViewModel() {
        fun mapError(throwable: Throwable): ErrorType = toErrorType(throwable)
    }

    private val viewModel = TestableBaseViewModel()

    @Test
    @Story("Error mapping")
    @DisplayName("UnknownHostException → NoInternetConnection")
    fun unknownHostMapsToNoInternet() {
        assertEquals(ErrorType.NoInternetConnection, viewModel.mapError(UnknownHostException()))
    }

    @Test
    @Story("Error mapping")
    @DisplayName("ConnectException → NoInternetConnection")
    fun connectExceptionMapsToNoInternet() {
        assertEquals(ErrorType.NoInternetConnection, viewModel.mapError(ConnectException()))
    }

    @Test
    @Story("Error mapping")
    @DisplayName("SocketTimeoutException → ConnectTimeout")
    fun socketTimeoutMapsToConnectTimeout() {
        assertEquals(ErrorType.ConnectTimeout, viewModel.mapError(SocketTimeoutException()))
    }

    @Test
    @Story("Error mapping")
    @DisplayName("Прочее исключение → UnknownError")
    fun genericMapsToUnknownError() {
        assertTrue(viewModel.mapError(IllegalStateException("boom")) is ErrorType.UnknownError)
    }
}
