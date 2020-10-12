package com.tmjee.myathena

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmjee.myathena.domain.Account
import com.tmjee.myathena.domain.User


class MainActivityViewModel @ViewModelInject() constructor(): ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    val account: MutableLiveData<Account> = MutableLiveData()
}