package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Cast
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val cast = MutableLiveData<List<Cast>>()
    private val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(id: String) {
        viewModelScope.launch {
            try {
                val favoriteMovie = userRepository.getMovieLocal(id)
                withContext(Dispatchers.Main) {
                    if (favoriteMovie?.isFavorite == true) {
                        val newMoview = movie.value
                        newMoview?.isFavorite = true
                        movie.value = newMoview
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

        favoriteChanged.value = true

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
        if (cast.value != null) return
        viewModelScope.launch {
            userRepository.getCastAndCrew(movieId)
                .fold({
                    cast.value = it.cast
                }, {
                    onError(it)
                })
        }
    }
}
