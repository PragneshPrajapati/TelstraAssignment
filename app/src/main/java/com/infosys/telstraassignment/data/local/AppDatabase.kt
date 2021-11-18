package com.infosys.telstraassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infosys.telstraassignment.data.CountryDetailsConverters
import com.infosys.telstraassignment.model.Country

@Database(entities = [Country::class], version = 1)
@TypeConverters(CountryDetailsConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
