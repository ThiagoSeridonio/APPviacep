package com.example.thiagoseridonio

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cep::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cepDao(): CepDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cep_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
