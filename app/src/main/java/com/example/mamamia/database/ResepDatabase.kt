package com.example.mamamia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ResepTable::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ResepDatabase : RoomDatabase() {
    abstract fun resepDao(): ResepDao
//    abstract fun stepDao(): StepDao

    companion object {
        @Volatile
        private var INSTANCE: ResepDatabase? = null

        fun getDatabase(context: Context): ResepDatabase? {
            if (INSTANCE == null) {
                synchronized(ResepDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ResepDatabase::class.java,
                        "resep_database"
                    ).    fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}