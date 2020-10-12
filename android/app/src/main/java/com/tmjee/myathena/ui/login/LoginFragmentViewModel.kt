package com.tmjee.myathena.ui.login

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tmjee.myathena.domain.LoginResult
import com.tmjee.myathena.repository.LoginRepo
import com.tmjee.myathena.repository.LoginRequest
import com.tmjee.myathena.repository.LoginResponse
import com.tmjee.myathena.ui.Evt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragmentViewModel @ViewModelInject constructor(
    val loginRepo: LoginRepo,
) : ViewModel() {

    companion object {
        const val EVT_TYPE_LOGIN_SUCCESS = "EVT_TYPE_LOGIN_SUCCESS"
        const val EVT_TYPE_USERNAME_VALIDATION_RESULT = "EVT_TYPE_USERNAME_VALIDATION_RESULT"
        const val EVT_TYPE_PASSWORD_VALIDATION_RESULT = "EVT_TYPE_PASSWORD_VALIDATION_RESULT"
        const val EVT_TYPE_FORM_VALIDATION_RESULT = "EVT_FORM_VALIDATION_RESULT"
        const val EVT_TYPE_FAILURE = "EVT_TYPE_FAILURE"
    }

    // inputs
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    // derivatives
    val usernameInvalid: LiveData<Boolean> = Transformations.map(username) { TextUtils.isEmpty(it) }
    val passwordInvalid: LiveData<Boolean> = Transformations.map(password) { TextUtils.isEmpty(it)}
    val modelInvalid: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun f(ignore: Boolean) {
            value = (usernameInvalid.value ?: true) || (passwordInvalid.value ?: true)
        }
       addSource(usernameInvalid, ::f)
       addSource(passwordInvalid, ::f)
    }
    val loginSuccess: MutableLiveData<LoginResult> = MutableLiveData()
    val failure: MutableLiveData<Throwable> = MutableLiveData()

    // events
    val events: MediatorLiveData<Evt<Any>> = MediatorLiveData<Evt<Any>>().apply {
        addSource(usernameInvalid) { value = Evt(EVT_TYPE_USERNAME_VALIDATION_RESULT, !it)}
        addSource(passwordInvalid) { value = Evt(EVT_TYPE_PASSWORD_VALIDATION_RESULT, !it)}
        addSource(modelInvalid) { value = Evt(EVT_TYPE_FORM_VALIDATION_RESULT, !it)}
        addSource(loginSuccess) { value = Evt(EVT_TYPE_LOGIN_SUCCESS, it)}
        addSource(failure) {value = Evt(EVT_TYPE_FAILURE, it)}
    }

    // actions
    fun login() {
        if (modelInvalid.value == false) {
            loginRepo.login(LoginRequest(username.value!!, password.value!!))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        loginSuccess.value = response.body()?.payload
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        failure.value = t
                    }
                })
        }
    }
}
