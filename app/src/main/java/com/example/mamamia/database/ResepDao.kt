package com.example.mamamia.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResepDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(resep : ResepTable)

    @Delete
    fun delete(resep : ResepTable)

    @Query("SELECT * from room_table")
    fun getAllResep (): LiveData<List<ResepTable>>

//    // Mencari resep berdasarkan kata kunci
//    @Query("SELECT * FROM room_table WHERE nama LIKE :query")
//    fun searchResep(query: String): LiveData<List<ResepEntity>>
//
//    // Mengambil resep berdasarkan ID
//    @Query("SELECT * FROM room_table WHERE id = :id")
//    fun getResepById(id: String): LiveData<ResepEntity>
//
//    // Update status bookmark berdasarkan ID
//    @Query("UPDATE room_table SET isBookmarked = :isBookmarked WHERE id = :id")
//    fun updateBookmarkStatus(id: String, isBookmarked: Boolean)
//
//    // Mengambil semua resep yang di-bookmark
//    @Query("SELECT * FROM room_table WHERE isBookmarked = 1")
//    fun getBookmarkedResep(): LiveData<List<ResepEntity>>
//
//    @Delete
//    fun deleteResep(resep: ResepEntity)
}
