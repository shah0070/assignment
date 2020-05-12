package com.shah.assignment.airtel.repository.service

import com.shah.assignment.airtel.model.AddressBaseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {

    @GET("compassLocation/rest/address/autocomplete")
    fun getCountriesAddress(@Query("queryString") qs: String): Single<AddressBaseModel>

}