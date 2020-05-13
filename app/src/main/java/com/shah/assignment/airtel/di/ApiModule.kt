package com.shah.assignment.airtel.di

import com.shah.assignment.airtel.repository.CountriesService
import com.shah.assignment.airtel.repository.service.CountriesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    private val BASE_URL = "https://digi-api.airtel.in"

    @Singleton
    @Provides
    fun provideCountriesApi(): CountriesApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCountriesService(): CountriesService {
        return CountriesService()
    }
}