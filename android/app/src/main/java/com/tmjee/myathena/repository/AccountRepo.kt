package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.Account
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class GetAccountsResponse constructor(
    override var status: String,
    val payload: List<Account>
):GenericResponse(status) {}

interface AccountRepo {

    @GET("/api/user/{userId}/accounts")
    fun getAccounts(@Path("userId") userId: Int): Call<GetAccountsResponse>

}