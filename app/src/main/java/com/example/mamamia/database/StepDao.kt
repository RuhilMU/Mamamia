//package com.example.mamamia.database
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//@Dao
//interface StepDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertStep(step: StepEntity)
//
//    @Query("SELECT * FROM step_table WHERE resepId = :resepId ORDER BY stepNumber ASC")
//    fun getStepsForResep(resepId: Int): LiveData<List<StepEntity>>
//
//    @Query("DELETE FROM step_table WHERE resepId = :resepId")
//    suspend fun deleteStepsByResepId(resepId: Int)
//}
