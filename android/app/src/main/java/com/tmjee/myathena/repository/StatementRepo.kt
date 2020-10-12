package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.Statement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class GetLoanStatementResponse(override var status: String, val payload: Statement)
: GenericResponse(status) {}

interface StatementRepo {

    @GET("/api/loan/{loanId}/statements")
    fun getLoanStatements(@Path("loanId")loanId: Int): Call<GetLoanDetailsResponse>
}