package com.infosys.telstraassignment.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.infosys.telstraassignment.MainCoroutinesRule
import com.infosys.telstraassignment.data.local.AppDatabase
import com.infosys.telstraassignment.data.local.CountryDao
import com.infosys.telstraassignment.model.Country
import com.infosys.telstraassignment.util.MockResponseFileReader
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CountryDetailsRepositoryTest {
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = mock(AppDatabase::class.java)
    }

    @Test
    fun checkCountryDetails() = runBlocking {
        val dao = mock(CountryDao::class.java)
        // Stub out database to return a mock dao.
        `when`(database.countryDao()).thenReturn(dao)

        val sampleCountryDetailsResponse = MockResponseFileReader("success_resp_list.json").content
        val countryDetails =
            Gson().fromJson(sampleCountryDetailsResponse, Country::class.java)

        assertNotNull(countryDetails)
        assertEquals(countryDetails.title, "About Canada")
    }
}
