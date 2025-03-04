package com.sample.ninjasapp.injection

import com.sample.ninjasapp.repository.AnimalRepository
import com.sample.ninjasapp.service.AnimalApi
import com.sample.ninjasapp.service.AnimalApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAnimalApi(): AnimalApi {
        return AnimalApiClient.instance
    }

    @Provides
    @Singleton
    fun provideAnimalRepository(api: AnimalApi): AnimalRepository {
        return AnimalRepository(api)
    }
}