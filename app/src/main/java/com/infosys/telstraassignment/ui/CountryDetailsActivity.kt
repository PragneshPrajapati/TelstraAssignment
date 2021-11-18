package com.infosys.telstraassignment.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.infosys.telstraassignment.databinding.ActivityMainBinding
import com.infosys.telstraassignment.model.CountryDetails
import com.infosys.telstraassignment.model.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CountryDetailsActivity : AppCompatActivity() {

    private val countryDetails = ArrayList<CountryDetails>()
    private val viewModel by viewModels<CountryDetailsViewModel>()
    private lateinit var countryDetailsAdapter: CountryDetailsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        setUpUIObserver()
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.fetchCountryDetails()
    }

    /**
     * Setup recyclerview
     */
    private fun init() {
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCountryDetails.layoutManager = layoutManager

        countryDetailsAdapter = CountryDetailsAdapter(this, countryDetails)
        binding.rvCountryDetails.adapter = countryDetailsAdapter
    }

    /**
     * Observer that observe data from view model
     */
    private fun setUpUIObserver() {
        viewModel.countryDetails.observe(
            this,
            { result ->

                if (result != null) {
                    when (result.status) {
                        Result.Status.SUCCESS -> {
                            result.data?.let { list ->
                                title = list.title
                                countryDetailsAdapter.updateData(list.rows)
                            }
                            hideLoading()
                        }

                        Result.Status.ERROR -> {
                            result.message?.let {
                                showError(it)
                            }
                            hideLoading()
                        }

                        Result.Status.LOADING -> {
                            showLoading()
                        }
                    }
                } else {
                    showLoading()
                }
            }
        )
    }

    /**
     * To show progressbar on api call
     */
    private fun showLoading() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.loading.visibility = View.GONE
        } else {
            binding.loading.visibility = View.VISIBLE
        }
    }

    /**
     * To hide progressbar once api call done
     */
    private fun hideLoading() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    /**
     * To notify user about error
     */
    private fun showError(msg: String) {
        Snackbar.make(binding.vParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}
