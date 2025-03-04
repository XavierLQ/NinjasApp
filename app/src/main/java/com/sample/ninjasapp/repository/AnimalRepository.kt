package com.sample.ninjasapp.repository


import com.sample.ninjasapp.model.Animal
import com.sample.ninjasapp.service.AnimalApi
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val api: AnimalApi
) {
    private val cache = mutableMapOf<String, Pair<List<Animal>, Long>>()
    private val cacheDurationMillis = 10 * 60 * 1000 // 10 minutes

    suspend fun getAnimals(name: String): List<Animal> {
        val cachedData = cache[name]
        val currentTime = System.currentTimeMillis()

        if (cachedData != null && (currentTime - cachedData.second) < cacheDurationMillis) {
            println("Cache hit for $name")
            return cachedData.first
        }

        println("Fetching from network for $name")
        val animals = api.getAnimals(name)
        cache[name] = Pair(animals, currentTime)
        return animals
    }
}