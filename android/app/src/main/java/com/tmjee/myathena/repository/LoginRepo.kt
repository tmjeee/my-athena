package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.LoginResult
import retrofit2.Call
import retrofit2.http.*

data class LoginResponse constructor(
    override var status: String,
    val payload: LoginResult
): GenericResponse(status) { }
data class LoginRequest constructor(val username: String, val password: String) {}

interface LoginRepo {
    @Headers("Accept: application/json")
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>;
}
