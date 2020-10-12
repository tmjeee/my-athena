package com.tmjee.myathena.ui.transactions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmjee.myathena.domain.Transactions
import com.tmjee.myathena.repository.GetTransactionResponse
import com.tmjee.myathena.repository.TransactionsRepo
import retrofit2.Call
import retrofit2.Response

class HomeDrawerTransactionsFragmentViewModel @ViewModelInject constructor(
    private val transactionRepo: TransactionsRepo): ViewModel() {

    val reloading: MutableLiveData<Boolean> = MutableLiveData(false)
    val transactions: MutableLiveData<Transactions> = MutableLiveData<Transactions>()
    val failures: MutableLiveData<Throwable> = MutableLiveData<Throwable>()

    fun reload(userId: Int, accountId: Int) {
        reloading.value = true;
        transactionRepo.getTransactions(accountId)
            .enqueue(object:retrofit2.Callback<GetTransactionResponse> {
                override fun onResponse( call: Call<GetTransactionResponse>, response: Response<GetTransactionResponse> ) {
                    try {
                      transactions.value = response.body()?.payload
                    } finally {
                      reloading.value = false
                    }
                }

                override fun onFailure(call: Call<GetTransactionResponse>, t: Throwable) {
                    failures.value = t
                    reloading.value = false
                }
            })
    }

    fun loadMore(userId: Int, accountId: Int) {
        transactionRepo.getTransactions(accountId)
            .enqueue(object:retrofit2.Callback<GetTransactionResponse> {
                override fun onResponse( call: Call<GetTransactionResponse>, response: Response<GetTransactionResponse> ) {
                    try {
                        if (transactions.value === null) {
                            transactions.value = response.body()?.payload
                        } else {
                            val currentCopy = transactions.value?.entries ?: emptyList();
                            val responseCopy = response.body()?.payload?.entries ?: emptyList();
                            val entries = listOf(
                                *currentCopy.toTypedArray(),
                                *responseCopy.toTypedArray()
                            )
                            transactions.value = response.body()?.let {
                                Transactions(
                                    it.payload.accountId,
                                    it.payload.total,
                                    it.payload.offset,
                                    it.payload.limit,
                                    entries
                                )
                            }
                        }
                    } finally {
                        reloading.value = false
                    }
                }

                override fun onFailure(call: Call<GetTransactionResponse>, t: Throwable) {
                    failures.value = t
                    reloading.value = false
                }
            })
    }

}