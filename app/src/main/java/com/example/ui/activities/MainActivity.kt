package com.example.ui.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.DateUtils
import com.example.R
import com.example.databinding.ActivityMainBinding
import com.example.di.ViewModelFactory
import com.example.model.bo.PlaceDataBo
import com.example.ui.dialogs.DatePickerFragment
import com.example.ui.fragments.CovidDataDetailFragment
import com.example.ui.fragments.CovidDataListFragment
import com.example.ui.viewmodels.DataListViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DataListViewModel>
    private val viewModel: DataListViewModel by viewModels { viewModelFactory }
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflateFragment(CovidDataListFragment(), false)
        binding.setupScreen()
    }

    fun showDetail() {
        inflateFragment(CovidDataDetailFragment(), true)
    }

    private fun ActivityMainBinding.setupScreen() {
        dateFrom.text = getString(R.string.date_neutral_label, DateUtils.getApiDateStringFormatted(viewModel.dateFrom))
        dateTo.text = getString(R.string.date_to_label, DateUtils.getApiDateStringFormatted(viewModel.dateTo))
        dateTo.visibility = if (viewModel.allowDateRange) View.VISIBLE else View.INVISIBLE
        dateFrom.setOnClickListener {
            showDatePickerDialog(getDatePickerListener
            { year, month, day ->
                val c = Calendar.getInstance()
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, month)
                c.set(Calendar.DAY_OF_MONTH, day)
                viewModel.dateFrom = c.time
                dateFrom.text = getString(
                    R.string.date_neutral_label,
                    DateUtils.getApiDateStringFormatted(viewModel.dateFrom)
                )
            }
            )
        }
        dateTo.setOnClickListener {
            showDatePickerDialog(getDatePickerListener
            { year, month, day ->
                val c = Calendar.getInstance()
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, month)
                c.set(Calendar.DAY_OF_MONTH, day)
                viewModel.dateTo = c.time
                dateTo.text = getString(
                    R.string.date_to_label,
                    DateUtils.getApiDateStringFormatted(viewModel.dateTo)
                )
            }
            )
        }

        allowDateRange.isChecked = viewModel.allowDateRange
        allowDateRange.setOnCheckedChangeListener { _, isChecked ->
            viewModel.allowDateRange = isChecked
            dateTo.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }

        searchButton.setOnClickListener {
            viewModel.getData()
        }

        configureTabLayout()
    }

    private fun ActivityMainBinding.configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("World"))
        tabLayout.addTab(tabLayout.newTab().setText("Spain"))
        tabLayout.addOnTabSelectedListener(getTabLayoutListener())
    }

    private fun getTabLayoutListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding?.let {
                    when (binding?.tabLayout?.selectedTabPosition) {
                        0 -> viewModel.dataMode = DataListViewModel.DataMode.WORLD_DATA
                        else -> viewModel.dataMode = DataListViewModel.DataMode.SPAIN_DATA
                    }
                }
            }

        }
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


    private fun showDatePickerDialog(
        listener: DatePickerDialog.OnDateSetListener,
    ) {
        val newFragment = DatePickerFragment(listener)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun getDatePickerListener(action: (year: Int, month: Int, day: Int) -> Unit): DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            action(year, month, day)
        }
}