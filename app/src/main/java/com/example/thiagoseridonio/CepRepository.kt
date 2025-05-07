package com.example.thiagoseridonio

import androidx.lifecycle.LiveData

class CepRepository(private val cepDao: CepDao) {

    val allCeps: LiveData<List<Cep>> = cepDao.getAllCeps()

    suspend fun insert(cep: Cep) {
        cepDao.insert(cep)
    }

    suspend fun update(cep: Cep) {
        cepDao.update(cep)
    }

    suspend fun delete(cep: Cep) {
        cepDao.delete(cep)
    }
}
