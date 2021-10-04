package com.example.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.DateUtils
import com.example.R
import com.example.databinding.DataListFragmentBinding
import com.example.di.ViewModelFactory
import com.example.model.bo.PlaceDataBo
import com.example.ui.activities.MainActivity
import com.example.ui.adapters.DataAdapter
import com.example.ui.adapters.DataListListener
import com.example.ui.viewmodels.DataListViewModel
import java.util.*
import javax.inject.Inject


class CovidDataListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DataListViewModel>
    private val viewModel: DataListViewModel by activityViewModels { viewModelFactory }
    private var binding: DataListFragmentBinding? = null
    private lateinit var mDataAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataListFragmentBinding.inflate(inflater, container, false)
        binding?.let {
            it.setupScreen()
            configureObservers()
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.allowDateRange?.setOnCheckedChangeListener(null)
        binding = null
    }

    private fun configureObservers() {
        viewModel.dataLiveData.observe(viewLifecycleOwner, Observer { result ->
            mDataAdapter.updateData(result)
        })
    }

    private fun DataListFragmentBinding.setupScreen() {
        mDataAdapter = DataAdapter(getDataListListener())

        dateFrom.text = getString(R.string.date_neutral_label, DateUtils.getApiDateStringFormatted(viewModel.dateFrom))
        dateTo.text = getString(R.string.date_to_label, DateUtils.getApiDateStringFormatted(viewModel.dateTo))
        dateTo.visibility = if (viewModel.allowDateRange) View.VISIBLE else View.INVISIBLE

        dateFrom.setOnClickListener { showDatePickerDialog(getDatePickerListener
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
        ) }
        dateTo.setOnClickListener { showDatePickerDialog(getDatePickerListener
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
        ) }

        allowDateRange.isChecked = viewModel.allowDateRange
        allowDateRange.setOnCheckedChangeListener { _, isChecked ->
            viewModel.allowDateRange = isChecked
            dateTo.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mDataAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        searchButton.setOnClickListener {
            viewModel.getData()
        }
    }

    private fun getDataListListener(): DataListListener {
        return object : DataListListener {
            override fun onItemSelected(item: PlaceDataBo) {
                viewModel.countrySelectedId = item.id
                (activity as? MainActivity)?.showDetail()
            }
        }
    }
}