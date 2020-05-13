package com.shah.assignment.airtel.repository

import com.shah.assignment.airtel.di.DaggerApiComponent
import com.shah.assignment.airtel.model.AddressBaseModel
import com.shah.assignment.airtel.repository.service.CountriesApi
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api: CountriesApi

    init {
        DaggerApiComponent.create().countryServiceInject(this)
    }

    public fun getCountries(queryAddress: String): Single<AddressBaseModel> {
        return api.getCountriesAddress(queryAddress)
    }

}