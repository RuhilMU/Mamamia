package com.example.mamamia.network

import com.example.mamamia.database.ResepEntity
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.Call

interface ApiService {

    // Ambil semua resep
    @GET("resep")
    fun getAllResep(): Call<List<ResepEntity>>

//    // Ambil detail resep berdasarkan ID
//    @GET("resep/{id}")
//    fun getResepById(
//        @Path("id") id: String,
//        @Body resep: ResepEntity
//    ): Call<ResepEntity>

    // Tambah resep baru
    @POST("resep")
    fun createResep(
        @Body resep: ResepEntity
    ): Call<ResepEntity>

//    // Upload gambar (mengembalikan URL gambar)
//    @Multipart
//    @POST("resep")
//    fun uploadImage(
//        @Part file: MultipartBody.Part
//    ): Call<ResepEntity>

    // Update resep
    @POST("resep/{id}")
    fun updateResep(
        @Path("id") resepId: String,
        @Body resep: ResepEntity
    ): Call<ResepEntity>

    // Hapus resep
    @DELETE("resep/{id}")
    fun deleteResep(
        @Path("id") resepId: String
    ): Call<ResepEntity>
}
