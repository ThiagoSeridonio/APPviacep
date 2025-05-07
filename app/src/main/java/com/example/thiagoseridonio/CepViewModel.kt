package com.example.thiagoseridonio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CepViewModel(private val repository: CepRepository) : ViewModel() {

    val allCeps: LiveData<List<Cep>> = repository.allCeps

    fun insert(cep: Cep) {
        viewModelScope.launch {
            repository.insert(cep)
        }
    }

    fun update(cep: Cep) {
        viewModelScope.launch {
            repository.update(cep)
        }
    }

    fun delete(cep: Cep) {
        viewModelScope.launch {
            repository.delete(cep)
        }
    }
}
