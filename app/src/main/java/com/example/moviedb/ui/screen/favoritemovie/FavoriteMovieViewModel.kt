package com.example.moviedb.ui.screen.favoritemovie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.loadmorerefresh.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class FavoriteMovieViewModel @ViewModelInject constructor(
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
