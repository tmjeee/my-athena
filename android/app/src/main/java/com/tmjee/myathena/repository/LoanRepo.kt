package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.LoanDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class GetLoanDetailsResponse(
    override var status: String,
    val payload: LoanDetails
)
: GenericResponse(status) {}

interface LoanRepo {

    @GET("/api/account/{accountId}/loan-details")
    fun getLoanDetails(@Path("accountId") accountId: Int): Call<GetLoanDetailsResponse>
}