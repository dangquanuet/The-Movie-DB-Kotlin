package com.quanda.moviedb.ui.main.login

import android.app.Application
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.PatternsCompat
import android.text.TextUtils
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.constants.Constants
import com.quanda.moviedb.data.source.UserRepository
import javax.inject.Inject

class LoginViewModel(application: Application,
        val loginNavigator: LoginNavigator) : BaseDataLoadViewModel(application) {

    class CustomFactory(val application: Application,
            val loginNavigator: LoginNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(application, loginNavigator) as T
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val formValid = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(email, { value = validateForm(email.value, password.value) })
        addSource(password, { value = validateForm(email.value, password.value) })
    }

    init {
        MainApplication.appComponent.inject(this)
    }

    private fun validateForm(email: String?, password: String?): Boolean = validateEmail(
            email) && validatePassword(password)

    private fun validateEmail(email: String?): Boolean = email != null
            && !TextUtils.isEmpty(email.trim())
            && PatternsCompat.EMAIL_ADDRESS.matcher(email.trim()).matches()

    private fun validatePassword(password: String?): Boolean = password != null
            && !TextUtils.isEmpty(password.trim())
            && password.trim().length >= Constants.MIN_PASSWORD_LENGTH
}