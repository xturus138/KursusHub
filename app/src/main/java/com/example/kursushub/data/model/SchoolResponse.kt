package com.example.kursushub.data.model

import com.google.gson.annotations.SerializedName

data class SchoolResponse(
    @SerializedName("dataSekolah")
    val dataSekolah: List<School>
)