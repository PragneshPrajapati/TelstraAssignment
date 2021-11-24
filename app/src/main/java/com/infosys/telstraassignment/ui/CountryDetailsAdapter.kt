package com.infosys.telstraassignment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.infosys.telstraassignment.R
import com.infosys.telstraassignment.model.CountryDetails

/**
 * Adapter class to bind country details to country recyclerview
 */
class CountryDetailsAdapter(
    private val context: FragmentActivity?,
    private val mCountryDetail: ArrayList<CountryDetails>
) : RecyclerView.Adapter<CountryDetailsAdapter.CountryDetailsViewHolder>() {

    class CountryDetailsViewHolder(private val context: FragmentActivity, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val description: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(countryDetails: CountryDetails) {
            title.text = countryDetails.title
            Glide.with(context).load(countryDetails.imageHref)
                .apply(
                    RequestOptions().override(400, 400).centerInside()
                        .placeholder(R.drawable.placeholder)
                ).into(itemView.findViewById(R.id.ivPoster))
            description.text = countryDetails.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryDetailsViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_country_details, parent, false)
        return CountryDetailsViewHolder(context!!, view)
    }

    override fun onBindViewHolder(
        holder: CountryDetailsViewHolder,
        position: Int
    ) {
        holder.bind(mCountryDetail[position])
    }

    override fun getItemCount(): Int = mCountryDetail.size

    /**
     * To update data source with latest data available
     */
    fun updateData(updatedCountryDetail: List<CountryDetails>) {
        mCountryDetail.clear()
        mCountryDetail.addAll(updatedCountryDetail)
        notifyDataSetChanged()
    }
}
