package com.infosys.telstraassignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.infosys.telstraassignment.MainCoroutinesRule
import com.infosys.telstraassignment.data.CountryDetailsRepository
import com.infosys.telstraassignment.model.Country
import com.infosys.telstraassignment.model.Result
import com.infosys.telstraassignment.util.MockResponseFileReader
import com.infosys.telstraassignment.util.getOrAwaitValue
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CountryDetailsViewModelTest {
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CountryDetailsRepository
    private lateinit var viewModel: CountryDetailsViewModel

    @Before
    fun setUp() {
        repository = Mockito.mock(CountryDetailsRepository::class.java)
        viewModel = Mockito.mock(CountryDetailsViewModel::class.java)
    }

    @Test
    fun checkCountryLiveData() {
        viewModel.countryDetails.value = getCountryDetails()
        val value = viewModel.countryDetails.getOrAwaitValue()
        assertNotNull(value)
    }

    private fun getCountryDetails(): Result<Country> {
        val sampleCountryDetailsResponse = MockResponseFileReader("success_resp_list.json").content
        val countryDetailsObject =
            Gson().fromJson(sampleCountryDetailsResponse, Country::class.java)
        return Result.success(countryDetailsObject)
    }
}
