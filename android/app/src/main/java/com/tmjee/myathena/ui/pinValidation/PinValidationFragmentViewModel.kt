package com.tmjee.myathena.ui.pinValidation

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tmjee.myathena.domain.User
import com.tmjee.myathena.repository.*
import com.tmjee.myathena.ui.Evt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinValidationFragmentViewModel @ViewModelInject constructor(
    val pinValidationRepo: PinValidationRepo,
): ViewModel() {

    companion object {
        val EVT_TYPE_PIN_VALIDATION = "EVT_TYPE_PIN_VALIDATION"
        val EVT_TYPE_FORM_VALIDATION = "EVT_TYPE_FORM_VALIDATION"
        val EVT_TYPE_REQUEST_PIN_SUCCESS = "EVT_TYPE_REQUEST_PIN_SUCCESS"
        val EVT_TYPE_REQUEST_PIN_FAILURE = "EVT_TYPE_REQUEST_PIN_FAILURE"
        val EVT_TYPE_SUBMIT_PIN_SUCCESS = "EVT_TYPE_SUBMIT_PIN_SUCCESS"
        val EVT_TYPE_SUBMIT_PIN_FAILURE = "EVT_TYPE_SUBMIT_PIN_FAILURE"
    }

    // arguments
    lateinit var args: PinValidationFragmentArgs

    // inputs
    val pin: MutableLiveData<String> = MutableLiveData<String>()

    // derivatives
    val pinInvalid: LiveData<Boolean> = Transformations.map(pin) {
        TextUtils.isEmpty(it)
    }
    val modelInvalid: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun f(ignore: Any) {
            value = pinInvalid.value
        }
        addSource(pinInvalid, ::f)
    }
    val requestPinSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val requestPinFailure: MutableLiveData<Throwable> = MutableLiveData()
    val submitPinSuccess: MutableLiveData<User> = MutableLiveData()
    val submitPinFailure: MutableLiveData<Throwable> = MutableLiveData()


    // events
    val events: MutableLiveData<Evt<Any>> = MediatorLiveData<Evt<Any>>().apply {
        addSource(pinInvalid) { value = Evt(EVT_TYPE_PIN_VALIDATION, !it) }
        addSource(modelInvalid) { value = Evt(EVT_TYPE_FORM_VALIDATION, !it) }
        addSource(requestPinSuccess) { value = Evt(EVT_TYPE_REQUEST_PIN_SUCCESS, it)}
        addSource(requestPinFailure) { value = Evt(EVT_TYPE_REQUEST_PIN_FAILURE, it)}
        addSource(submitPinSuccess) { value = Evt(EVT_TYPE_SUBMIT_PIN_SUCCESS, it)}
        addSource(submitPinFailure) { value = Evt(EVT_TYPE_SUBMIT_PIN_FAILURE, it)}
    }

    // actions
    fun requestPin() {
        pinValidationRepo.requestPin(args.userId).enqueue(object: Callback<RequestPinResponse> {
            override fun onResponse( call: Call<RequestPinResponse>, response: Response<RequestPinResponse> ) {
                requestPinSuccess.value = response.body()!!.isOk()
            }
            override fun onFailure(call: Call<RequestPinResponse>, t: Throwable) {
                requestPinFailure.value = t
            }
        })
    }

    fun submitPin() {
        if (modelInvalid.value == false) {
            val pin: String = pin.value!!;
            pinValidationRepo.submitPin(SubmitPinRequest(pin, args.userId))
                .enqueue(object : Callback<SubmitPinResponse> {
                    override fun onResponse(
                        call: Call<SubmitPinResponse>,
                        response: Response<SubmitPinResponse>
                    ) {
                        submitPinSuccess.value = response.body()!!.payload
                    }

                    override fun onFailure(call: Call<SubmitPinResponse>, t: Throwable) {
                        submitPinFailure.value = t
                    }
                })
        }
    }
}
