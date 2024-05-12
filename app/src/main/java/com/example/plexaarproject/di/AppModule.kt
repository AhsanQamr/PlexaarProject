package com.example.plexaarproject.di

import com.example.plexaarproject.repository.PlexarRepository
import com.example.plexaarproject.repository.PlexarRepositoryImpl
import com.example.plexaarproject.retrofit.PlexaarApi
import com.example.plexaarproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlexaar(): PlexaarApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlexaarApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(plexaarApi: PlexaarApi): PlexarRepository {
        return PlexarRepositoryImpl(plexaarApi)
    }




}