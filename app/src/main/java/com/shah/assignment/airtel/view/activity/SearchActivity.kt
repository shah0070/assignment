package com.shah.assignment.airtel.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shah.assignment.airtel.view.fragment.SearchFragment
import com.shah.androidarchitectures.R

class SearchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchactivity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.layout_container, SearchFragment()).commit()
        }
    }


}