//package com.example.mamamia.database
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.mamamia.request.StepRequest
//
//@Entity(tableName = "step_table")
//data class StepEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val resepId: String,
//    val stepNumber: Int,
//    val description: String
//) {
//    fun toRequest(serverResepId: String): StepRequest {
//        return StepRequest(
//            id = id,
//            stepNumber = stepNumber,
//            resepId = serverResepId,
//            description = description
//        )
//    }
//}
//
