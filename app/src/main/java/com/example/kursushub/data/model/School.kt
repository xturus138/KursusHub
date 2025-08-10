package com.example.kursushub.data.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("sekolah")
    val namaSekolah: String? = null,

    @SerializedName("bentuk")
    val jenjang: String? = null,

    @SerializedName("kabupaten_kota")
    val kota: String? = null,

    @SerializedName("propinsi")
    val provinsi: String? = null,

    var status: String? = null
)