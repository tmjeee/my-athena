package com.tmjee.myathena.ui.hub

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tmjee.myathena.databinding.RecyclerViewHubEntryBinding
import com.tmjee.myathena.domain.Account
import com.tmjee.myathena.domain.User
import com.tmjee.myathena.repository.AccountRepo
import com.tmjee.myathena.repository.GetAccountsResponse
import com.tmjee.myathena.ui.Evt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HubFragmentViewModel @ViewModelInject constructor(private val accountRepo: AccountRepo): ViewModel() {

    companion object {
        val EVT_TYPE_ACCOUNTS = "EVT_TYPE_ACCOUNTS"
        val EVT_TYPE_FAILURE = "EVT_TYPE_FAILURE"
        val EVT_TYPE_REFRESHING = "EVT_TYPE_REFRESHING"
        val EVT_TYPE_EMPTY = "EVT_TYPE_EMPTY"
        val EVT_TYPE_ACCOUNT_SELECTED = "EVT_TYPE_ACCOUNT_SELECTED"
    }

    // args
    lateinit var args: HubFragmentArgs

    // input

    // derivatives
    val accounts: MutableLiveData<List<Account>> = MutableLiveData(emptyList())
    val failures: MutableLiveData<Throwable> = MutableLiveData()
    val refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val empty: LiveData<Boolean> = Transformations.map(accounts) {
       it?.isEmpty() ?: true
    }
    val accountSelected: MutableLiveData<Account> = MutableLiveData()

    // events
    val events = MediatorLiveData<Evt<Any>>().apply {
        addSource(accounts) { value = Evt(EVT_TYPE_ACCOUNTS, it)}
        addSource(failures) { value = Evt(EVT_TYPE_FAILURE, it)}
        addSource(refreshing) { value = Evt(EVT_TYPE_REFRESHING, it)}
        addSource(empty) { value = Evt(EVT_TYPE_EMPTY, it)}
        addSource(accountSelected) { value = Evt(EVT_TYPE_ACCOUNT_SELECTED, it)}
    }

    // actions
    fun reload(userId: Int) {
       refreshing.value = true;
       accountRepo.getAccounts(userId).enqueue(object:Callback<GetAccountsResponse> {
           override fun onResponse( call: Call<GetAccountsResponse>, response: Response<GetAccountsResponse> ) {
               accounts.value = response.body()!!.payload
               refreshing.value = false
           }
           override fun onFailure(call: Call<GetAccountsResponse>, t: Throwable) {
               failures.value = t
               refreshing.value = false
           }
       })
    }

    fun accountSelected(account: Account) {
        accountSelected.value = account
    }



}