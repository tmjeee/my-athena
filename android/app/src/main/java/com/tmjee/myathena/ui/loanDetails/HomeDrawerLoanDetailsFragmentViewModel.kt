package com.tmjee.myathena.ui.loanDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmjee.myathena.domain.LoanDetails
import com.tmjee.myathena.repository.GetLoanDetailsResponse
import com.tmjee.myathena.repository.LoanRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDrawerLoanDetailsFragmentViewModel @ViewModelInject constructor(val loanRepo: LoanRepo): ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val failure: MutableLiveData<Throwable> = MutableLiveData()
    val loanDetails: MutableLiveData<LoanDetails> = MutableLiveData()

    fun loadLoanDetails(accountId: Int) {
        loading.value = true
        loanRepo.getLoanDetails(accountId)
            .enqueue(object: Callback<GetLoanDetailsResponse> {
            override fun onResponse( call: Call<GetLoanDetailsResponse>, response: Response<GetLoanDetailsResponse>) {
                println("============================****** payload ${response.body()?.payload}")
                try {
                    loanDetails.value = response.body()?.payload
                } finally {
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<GetLoanDetailsResponse>, t: Throwable) {
                t.printStackTrace()
                try {
                    failure.value = t
                } finally {
                    loading.value = false
                }
            }
        })
    }

}