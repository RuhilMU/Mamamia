//package com.example.mamamia.database
//
//import androidx.room.Embedded
//import androidx.room.Relation
//
//data class ResepStep (
//    @Embedded val resep: ResepEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "resepId"
//    )
//    val steps: List<StepEntity>
//)