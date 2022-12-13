package com.example.moviedb.compose.ui.detail

import androidx.lifecycle.viewModelScope
import com.example.moviedb.compose.ui.base.StateViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : StateViewModel() {
    private val _movie by lazy { MutableStateFlow<Movie?>(null) }
    val movie: StateFlow<Movie?> = _movie

    private var movieId: String? = null

    fun setValueMovieId(id: String?) {
        movieId = id
    }

    fun getMovieDetail(movieId: String?) {
        if (movieId.isNullOrBlank()) return
        viewModelScope.launch {
            try {
                showLoading()
                _movie.value = userRepository.getMovieById(movieId = movieId)
            } catch (e: Exception) {
                onError(e)
            } finally {
                hideLoading()
            }
        }
    }

    override fun doRefresh() {
        movieId?.let { id ->
            if (movieId.isNullOrBlank()) return
            viewModelScope.launch {
                try {
                    showRefreshing()
                    _movie.value = userRepository.getMovieById(movieId = id)
                } catch (e: Exception) {
                    onError(e)
                } finally {
                    hideRefreshing()
                }
            }
        }
    }
}
