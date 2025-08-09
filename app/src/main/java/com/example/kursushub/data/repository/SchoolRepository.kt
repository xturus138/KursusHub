package com.example.kursushub.data.repository

import com.example.kursushub.data.model.School
import com.example.kursushub.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SchoolRepository {
    private val apiService: ApiService

    init {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-sekolah-indonesia.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getSchools(jenjang: String?, page: Int): List<School> {
        val path = if (jenjang.isNullOrBlank()) "sekolah" else "sekolah/$jenjang"
        return apiService.getSchools(path = path, page = page).dataSekolah
    }

    suspend fun searchSchools(query: String): List<School> {
        return apiService.searchSchools(query).dataSekolah
    }
}