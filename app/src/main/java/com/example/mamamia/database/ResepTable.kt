package com.example.mamamia.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "room_table")
@TypeConverters(Converters::class)
data class ResepTable(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "deskripsi")
    val deskripsi: String,

    @ColumnInfo(name = "penulis")
    val penulis: String,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<String>,

    @ColumnInfo(name = "steps")
    val steps: List<String>,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "servings")
    val servings: String,

    @ColumnInfo(name = "gambar")
    val gambar: String,

//    @ColumnInfo(name = "isBookmarked")
//    var isBookmarked: Boolean = false,
)