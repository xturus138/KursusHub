package com.example.kursushub.data.remote

import com.example.kursushub.data.model.SchoolResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{path}")
    suspend fun getSchools(
        @Path(value = "path", encoded = true) path: String,
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 20
    ): SchoolResponse

    @GET("sekolah/s")
    suspend fun searchSchools(
        @Query("sekolah") query: String
    ): SchoolResponse
}