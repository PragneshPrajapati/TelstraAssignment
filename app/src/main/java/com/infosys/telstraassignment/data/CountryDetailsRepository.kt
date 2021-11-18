package com.infosys.telstraassignment.data

import com.infosys.telstraassignment.data.local.CountryDao
import com.infosys.telstraassignment.data.remote.CountryRemoteDataSource
import com.infosys.telstraassignment.model.Country
import com.infosys.telstraassignment.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CountryDetailsRepository @Inject constructor(
    private val countryRemoteDataSource: CountryRemoteDataSource,
    private val countryDao: CountryDao
) {

    /**
     * Api call to get country details from server
     */
    suspend fun fetchCountryDetails(): Flow<Result<Country>?> {
        return flow {
            emit(fetchCountryDetailsCached())
            emit(Result.loading())
            val result = countryRemoteDataSource.fetchCountry()

            // Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    countryDao.deleteAll(it)
                    countryDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * To fetch data from local DB
     */
    private fun fetchCountryDetailsCached(): Result<Country>? =
        countryDao.getAll()?.let {
            Result.success(it)
        }
}
