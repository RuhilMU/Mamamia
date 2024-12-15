package com.example.mamamia.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@TypeConverters(Converters::class)
@Entity(tableName = "resep_table")
data class ResepEntity(
    @SerializedName("_id")
    val id: String ?  = null,

    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("penulis")
    val penulis: String,

    @SerializedName("ingredients")
    val ingredients: List<String>,

    @SerializedName("steps")
    val steps: List<String>,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("servings")
    val servings: String,

    @SerializedName("gambar")
    val gambar: String,

//    @SerializedName("bookmark")
//    var isBookmarked: Boolean = false,
) : Serializable
