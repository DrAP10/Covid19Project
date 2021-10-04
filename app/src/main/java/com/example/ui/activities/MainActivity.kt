package com.example.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.R
import com.example.model.bo.PlaceDataBo
import com.example.ui.fragments.CovidDataDetailFragment
import com.example.ui.fragments.CovidDataListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inflateFragment(CovidDataListFragment(), false)
    }

    fun showDetail() {
        inflateFragment(CovidDataDetailFragment(), true)
    }

    private fun inflateFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.container, fragment)
        if (addToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commitAllowingStateLoss()
    }
}