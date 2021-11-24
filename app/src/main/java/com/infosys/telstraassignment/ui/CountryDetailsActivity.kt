package com.infosys.telstraassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infosys.telstraassignment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
