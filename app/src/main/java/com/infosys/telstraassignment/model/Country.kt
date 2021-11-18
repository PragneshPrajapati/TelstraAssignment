package com.infosys.telstraassignment.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val rows: List<CountryDetails>
)
