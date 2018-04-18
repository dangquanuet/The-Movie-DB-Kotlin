package com.quanda.moviedb.ui.main.moviedetail

import android.app.Application
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.PatternsCompat
import android.text.TextUtils
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository

class MovieDetailViewModel(application: Application,
        val movieDetailNavigator: MovieDetailNavigator) : BaseDataLoadViewModel(application) {

    class CustomFactory(val application: Application,
            val movieDetailNavigator: MovieDetailNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(application, movieDetailNavigator) as T
        }
    }

    val movie = MutableLiveData<Movie>()
    val userRepository = UserRepository.getInstance(application)

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val formValid = MediatorLiveData<Boolean>().apply {
        addSource(email, { value = validateForm(email.value, password.value) })
        addSource(password, { value = validateForm(email.value, password.value) })
    }

    private fun validateForm(email: String?, password: String?): Boolean = validateEmail(
            email) && validatePassword(password)

    private fun validateEmail(email: String?): Boolean = email != null
            && !TextUtils.isEmpty(email.trim()) && PatternsCompat.EMAIL_ADDRESS.matcher(
            email.trim()).matches()

    private fun validatePassword(password: String?): Boolean = password != null
            && !TextUtils.isEmpty(
            password.trim()) && password.length > 8

}