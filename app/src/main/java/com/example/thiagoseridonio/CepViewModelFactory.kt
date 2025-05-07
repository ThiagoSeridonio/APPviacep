package com.example.thiagoseridonio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CepViewModelFactory(private val repository: CepRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CepRepository::class.java).newInstance(repository)
    }
}
