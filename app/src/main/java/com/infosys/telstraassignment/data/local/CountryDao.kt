package com.infosys.telstraassignment.data.local

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.infosys.telstraassignment.model.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM country")
    fun getAll(): Country?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(country: Country)

    @Delete
    fun deleteAll(country: Country)
}
