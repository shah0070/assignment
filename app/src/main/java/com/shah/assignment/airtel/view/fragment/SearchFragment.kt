package com.shah.assignment.airtel.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shah.androidarchitectures.R
import com.shah.assignment.airtel.view.adapter.SearchAdapter
import com.shah.assignment.airtel.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.searchfragment.*

class SearchFragment : Fragment() {
    lateinit var searchViewModel: SearchViewModel
    private val searchAdapter = SearchAdapter(arrayListOf())
    lateinit var viewSearchFragment: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewSearchFragment = inflater.inflate(R.layout.searchfragment, container, false)

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        return viewSearchFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addRecyclerViewLayout()
        bindingview()
        observeViewModel()
    }

    fun addRecyclerViewLayout() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchAdapter
        }
    }

    fun bindingview() {
        searchViewModel.let {
            searchViewModel.setSearchViewwithFilter(search_view)
            searchViewModel.setcloseView(close_view)
        }
    }

    fun observeViewModel() {
        searchViewModel.let {
            searchViewModel.countries.let {
                searchViewModel.countries.observe(this, Observer { countries ->
                    countries?.let {
                        searchAdapter.updateCountries(it)
                        if (it.isNotEmpty()) {
                            data_not_found_view.visibility = View.GONE
                        } else {
                            data_not_found_view.visibility = View.VISIBLE
                        }
                    }
                })
            }
            searchViewModel.countryLoadError.let {
                searchViewModel.countryLoadError.observe(this, Observer { isError ->
                    isError?.let {
                        if (it) {
                            Toast.makeText(activity,it.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }

            searchViewModel.loading.let {
                searchViewModel.loading.observe(this, Observer { countries ->
                    countries?.let {
                        if (it) {
                            progress_bar.visibility = View.VISIBLE
                        } else {
                            progress_bar.visibility = View.GONE
                        }
                    }
                })
            }
        }

    }
}