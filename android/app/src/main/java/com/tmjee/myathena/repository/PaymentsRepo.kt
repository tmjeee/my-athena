package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.Payments
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PaymentsRepo {

    @GET("/api/account/{accountId}/payments")
    fun getPayments(@Path("accountId") accountId: Int,
                    @Query("limit") limit: Int = 5,
                    @Query("offset") offset: Int = 0): Call<Payments>;
}