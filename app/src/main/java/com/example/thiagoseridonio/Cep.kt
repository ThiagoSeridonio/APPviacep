package com.example.thiagoseridonio

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cep")
data class Cep(
    @PrimaryKey
    @ColumnInfo(name = "cep_codigo")
    val cep: String,

    @ColumnInfo(name = "logradouro")
    val logradouro: String,

    @ColumnInfo(name = "bairro")
    val bairro: String,

    @ColumnInfo(name = "cidade")
    val cidade: String,

    @ColumnInfo(name = "uf")
    val uf: String
)
