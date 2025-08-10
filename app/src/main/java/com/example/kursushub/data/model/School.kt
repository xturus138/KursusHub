package com.example.kursushub.data.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("sekolah")
    val namaSekolah: String?,

    @SerializedName("bentuk")
    val jenjang: String?,

    @SerializedName("kabupaten_kota")
    val kota: String?,

    @SerializedName("propinsi")
    val provinsi: String?,

    var status: String? = null
)