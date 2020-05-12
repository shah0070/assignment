package com.shah.assignment.airtel.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shah.androidarchitectures.R
import com.shah.assignment.airtel.model.AddressList
import kotlinx.android.synthetic.main.searchiteam.view.*


class SearchAdapter(var addressList: ArrayList<AddressList>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    fun updateCountries(newCountries: List<AddressList>) {
        addressList.clear()
        addressList.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.searchiteam, parent, false)
    )

    override fun getItemCount() = addressList.let { it.size }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (addressList != null) {
            holder.let { it.bind(addressList[position]) }
        }
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val raw_city_name = view.raw_city_name
        private val country_address = view.country_address

        fun bind(addressList: AddressList) {
            addressList.let {
                addressList.city.let {
                    raw_city_name.text = addressList.city
                }
                addressList.addressString.let {
                    country_address.text = addressList.addressString
                }
            }
        }
    }
}