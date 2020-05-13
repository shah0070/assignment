package com.shah.assignment.airtel.di


import com.shah.assignment.airtel.viewmodel.SearchViewModel
import com.shah.assignment.airtel.repository.CountriesService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun countryServiceInject(service: CountriesService)

    fun viewModelInject(viewModel: SearchViewModel)
}