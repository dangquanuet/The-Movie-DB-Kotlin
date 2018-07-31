package com.quanda.moviedb.ui.screen.main.login

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v4.util.PatternsCompat
import android.text.TextUtils
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.constants.Constants
import com.quanda.moviedb.data.repository.impl.UserRepositoryImpl
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class LoginViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var userRepository: UserRepositoryImpl

    lateinit var navigator: LoginNavigator

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