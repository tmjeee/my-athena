package com.tmjee.myathena.repository

import com.tmjee.myathena.domain.PhotoSet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PhotosRepo {

    @GET("/api/account/{accountId}/photos")
    fun getPhotos(
        @Path("accountId") accountId: String,
        @Query("limit") limit: Int = 5,
        @Query("offset") offset: Int = 0): Call<PhotoSet>;
}