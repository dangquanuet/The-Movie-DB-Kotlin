package com.example.moviedb.ui.screen.favoritemovie

import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.loadmorerefresh.BaseLoadMoreRefreshViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        viewModelScope.launch {
            try {
                onLoadSuccess(
                    page = page,
                    items = userRepository.getFavoriteLocal(
                        pageSize = getNumberItemPerPage(),
                        pageIndex = page
                    )
                )
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

}
