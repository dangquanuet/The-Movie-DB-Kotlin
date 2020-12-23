package com.example.moviedb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
open class BaseViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}
