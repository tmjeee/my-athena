package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.User
import retrofit2.Call
import retrofit2.http.*

class RequestPinResponse constructor(status: String): GenericResponse(status) {}

data class SubmitPinRequest constructor(val pin: String, val userId: Int) {}
data class SubmitPinResponse constructor(
    override var status: String,
    val payload: User
): GenericResponse(status) {}

interface PinValidationRepo {

    @Headers("Accept: application/json")
    @GET("/api/requestPin/{userId}")
    fun requestPin(@Path("userId") userId: Int): Call<RequestPinResponse>

    @Headers("Accept: application/json")
    @POST("/api/submitPin")
    fun submitPin(@Body request: SubmitPinRequest): Call<SubmitPinResponse>
}
