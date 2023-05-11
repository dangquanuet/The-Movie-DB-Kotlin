package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Cast
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val movie = MutableStateFlow<Movie?>(null)
    private val _castList = MutableStateFlow<List<Cast>>(emptyList())
    val castList = _castList.asStateFlow()

    fun checkFavorite(id: String) {
        viewModelScope.launch {
            try {
                val favoriteMovie = userRepository.getMovieLocal(id)
                withContext(Dispatchers.Main) {
                    if (favoriteMovie?.isFavorite == true) {
                        val newMovie = movie.value
                        newMovie?.isFavorite = true
                        movie.emit(newMovie)
                    } else {
                        movie.value?.isFavorite = false
                    }
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun favoriteMovie() {
        val newMovie = movie.value
        newMovie?.isFavorite = movie.value?.isFavorite != true
        movie.value = newMovie

        newMovie?.let {
            viewModelScope.launch {
                try {
                    userRepository.updateDB(it)
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }

    /**
     * load cast and crew
     */
    fun loadCastAndCrew(movieId: String) {
        if (_castList.value.isNotEmpty()) return
        viewModelScope.launch {
            try {
                _castList.emit(userRepository.getCastAndCrew(movieId).cast ?: emptyList())
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}
