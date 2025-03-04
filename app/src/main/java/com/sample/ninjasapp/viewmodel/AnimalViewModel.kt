package com.sample.ninjasapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.sample.ninjasapp.model.Animal
import com.sample.ninjasapp.repository.AnimalRepository

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val repository: AnimalRepository
) : ViewModel() {

    var dogList = mutableStateOf<List<Animal>>(emptyList())
    var birdList = mutableStateOf<List<Animal>>(emptyList())
    var bugList = mutableStateOf<List<Animal>>(emptyList())
    var searchTerm = mutableStateOf("")

    init {
        fetchAnimals()
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            dogList.value = repository.getAnimals("dog")
            birdList.value = repository.getAnimals("bird")
            bugList.value = repository.getAnimals("bug")
        }
    }

    fun filterAnimals(animals: List<Animal>): List<Animal> {
        return if (searchTerm.value.isNotEmpty()) {
            animals.filter { animal ->
                animal.name.contains(searchTerm.value, ignoreCase = true) ||
                        (animal.characteristics.common_name?.contains(searchTerm.value, ignoreCase = true) ?: false)
            }
        } else {
            animals
        }
    }

    fun onSearchTermChange(newSearchTerm: String) {
        searchTerm.value = newSearchTerm
    }
}