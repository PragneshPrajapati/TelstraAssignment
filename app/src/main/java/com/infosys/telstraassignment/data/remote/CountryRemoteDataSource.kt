package com.infosys.telstraassignment.data.remote

import com.infosys.telstraassignment.model.Country
import com.infosys.telstraassignment.model.Result
import com.infosys.telstraassignment.network.services.APIEndPointsService
import com.infosys.telstraassignment.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CountryRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {
    suspend fun fetchCountry(): Result<Country> {
        val countryService = retrofit.create(APIEndPointsService::class.java)
        return getResponse(
            request = { countryService.getCountryDetails() },
            defaultErrorMessage = "Error fetching Country Details"
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}
