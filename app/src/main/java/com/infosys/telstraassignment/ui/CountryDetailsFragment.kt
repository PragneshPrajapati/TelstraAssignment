package com.infosys.telstraassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.infosys.telstraassignment.R
import com.infosys.telstraassignment.databinding.FragmentCountryDetailsBinding
import com.infosys.telstraassignment.model.CountryDetails
import com.infosys.telstraassignment.model.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

/**
 * A simple [Fragment] subclass to display country details
 */
@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private val countryDetails = ArrayList<CountryDetails>()
    private val viewModel by viewModels<CountryDetailsViewModel>()
    private lateinit var countryDetailsAdapter: CountryDetailsAdapter
    private lateinit var binding: FragmentCountryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountryDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setUpUIObserver()
    }

    /**
     * Setup recyclerview
     */
    private fun init() {
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        val layoutManager = LinearLayoutManager(context)
        binding.rvCountryDetails.layoutManager = layoutManager

        countryDetailsAdapter = CountryDetailsAdapter(activity, countryDetails)
        binding.rvCountryDetails.adapter = countryDetailsAdapter
    }

    /**
     * Observer that observe data from view model
     */
    private fun setUpUIObserver() {
        viewModel.countryDetails.observe(
            viewLifecycleOwner,
            { result ->

                if (result != null) {
                    when (result.status) {
                        Result.Status.SUCCESS -> {
                            result.data?.let { list ->
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
        Snackbar.make(binding.vParent, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.action_btn_dismiss)) {
            }.show()
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.fetchCountryDetails()
    }
}
