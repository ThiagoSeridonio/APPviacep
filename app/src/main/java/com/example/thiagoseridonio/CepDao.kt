package com.example.thiagoseridonio

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface CepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cep: Cep)

    @Update
    suspend fun update(cep: Cep)

    @Delete
    suspend fun delete(cep: Cep)

    @Query("SELECT * FROM cep WHERE cep_codigo = :cep LIMIT 1")
    suspend fun getCep(cep: String): Cep?

}
