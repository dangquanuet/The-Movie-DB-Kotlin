package com.example.moviedb.ui.popularmovie

import androidx.lifecycle.Observer
import com.example.moviedb.BaseViewModelTest
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.factory.createMovieListResponse
import com.example.moviedb.mock
import com.example.moviedb.ui.screen.popularmovie.PopularMovieViewModel
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class PopularMovieViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: PopularMovieViewModel

    private val userRepository = mock<UserRepository>()

    override fun setup() {
        super.setup()
        viewModel = PopularMovieViewModel(userRepository)
    }

    @Test
    fun getDataSuccessTest() {
        testCoroutineRule.runBlockingTest {
            // given
            val fakeData = createMovieListResponse()
            val page = 1
            viewModel.mode.value = MovieListType.POPULAR.type

            val hashMap = HashMap<String, String>()
            hashMap[ApiParams.PAGE] = page.toString()
            when (viewModel.mode.value) {
                MovieListType.POPULAR.type -> hashMap[ApiParams.SORT_BY] = ApiParams.POPULARITY_DESC
                MovieListType.TOP_RATED.type -> hashMap[ApiParams.SORT_BY] = ApiParams.VOTE_AVERAGE_DESC
                else -> hashMap[ApiParams.SORT_BY] = ApiParams.POPULARITY_DESC
            }
            val observer = mock<Observer<List<Movie>>>()
            viewModel.listItem.observeForever(observer)

            Mockito.`when`(userRepository.getMovieList(hashMap)).thenReturn(fakeData)

            // when
            viewModel.loadData(page)

            // then
            Assert.assertEquals(4, viewModel.listItem.value?.size)
            Assert.assertEquals("1", viewModel.listItem.value?.get(0)?.id)
            Assert.assertEquals("2", viewModel.listItem.value?.get(1)?.id)
            Assert.assertEquals("3", viewModel.listItem.value?.get(2)?.id)
            Assert.assertEquals("4", viewModel.listItem.value?.get(3)?.id)

            Mockito.verify(observer).onChanged(fakeData.results)
        }
    }
}