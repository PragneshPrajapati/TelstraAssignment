package com.infosys.telstraassignment.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.infosys.telstraassignment.model.CountryDetails

/**
 * converts List to and from String
 */
class CountryDetailsConverters {
    @TypeConverter
    fun countryDetailsToString(value: List<CountryDetails>?): String = Gson().toJson(value)

    @TypeConverter
    fun stringToCountryDetails(value: String) =
        Gson().fromJson(value, Array<CountryDetails>::class.java).toList()
}
