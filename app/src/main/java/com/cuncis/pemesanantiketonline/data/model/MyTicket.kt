package com.cuncis.pemesanantiketonline.data.model

data class MyTicket(val nama_wisata: String? = "",
                    val lokasi: String? = "",
                    val jumlah_tiket: Int? = 0) {
    override fun toString(): String {
        return "MyTicket(namaWisata=$nama_wisata, lokasi=$lokasi, jumlahTiket=$jumlah_tiket)"
    }
}