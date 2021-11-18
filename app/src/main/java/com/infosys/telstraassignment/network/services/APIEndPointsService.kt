package com.infosys.telstraassignment.network.services

import com.infosys.telstraassignment.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface APIEndPointsService {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun getCountryDetails(): Response<Country>
}
