package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.Transactions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class GetTransactionResponse(
    override var status: String,
    val payload: Transactions
): GenericResponse(status) { }

interface TransactionsRepo {

    @GET("/api/account/{accountId}/transactions")
    fun getTransactions(@Path("accountId") accountId: Int): Call<GetTransactionResponse>

}