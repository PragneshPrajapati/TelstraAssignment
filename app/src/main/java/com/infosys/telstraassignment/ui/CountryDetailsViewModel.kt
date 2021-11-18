package com.infosys.telstraassignment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infosys.telstraassignment.R
import com.infosys.telstraassignment.TelstraAssignmentApp
import com.infosys.telstraassignment.data.CountryDetailsRepository
import com.infosys.telstraassignment.model.Country
import com.infosys.telstraassignment.model.Result
import com.infosys.telstraassignment.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(private val countryRepository: CountryDetailsRepository) :
    ViewModel() {

    private val _countryDetails = MutableLiveData<Result<Country>>()
    val countryDetails = _countryDetails

    private val networkHelper = NetworkHelper(TelstraAssignmentApp.appContext!!)

    init {
        fetchCountryDetails()
    }

    fun fetchCountryDetails() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                countryRepository.fetchCountryDetails().collect {
                    _countryDetails.value = it
                }
            }
        } else {
            _countryDetails.postValue(
                Result.error(
                    TelstraAssignmentApp.appContext!!.getString(
                        R.string.network_error_msg
                    ),
                    null
                )
            )
        }
    }
}
