package com.example.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.DateUtils
import com.example.R
import com.example.databinding.DataDetailFragmentBinding
import com.example.di.ViewModelFactory
import com.example.model.bo.DataResponseBo
import com.example.model.bo.PlaceDataBo
import com.example.ui.viewmodels.DataListViewModel
import java.util.*
import javax.inject.Inject


class CovidDataDetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DataListViewModel>
    private val viewModel: DataListViewModel by activityViewModels { viewModelFactory }
    private var binding: DataDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataDetailFragmentBinding.inflate(inflater, container, false)
        binding?.setupScreen()
        configureObservers()
        return binding?.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = getString(R.string.app_name)
        viewModel.countrySelectedId = null
        binding?.allowDateRange?.setOnCheckedChangeListener(null)
        binding = null
    }

    private fun configureObservers() {
        viewModel.dataLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding?.updateData(result)
        })
    }

    private fun DataDetailFragmentBinding.updateData(dataResponse: DataResponseBo) {
        dataResponse.placesData.firstOrNull { viewModel.countrySelectedId == it.id }?.let {
            name.text = it.name
            confirmed.text = getString(R.string.data_confirmed_label, it.confirmed)
            death.text = getString(R.string.data_death_label, it.deaths)
            newConfirmed.text = getString(R.string.data_new_confirmed_label, it.newConfirmed)
            newDeaths.text = getString(R.string.data_new_death_label, it.newDeaths)
            newOpenCases.text = getString(R.string.data_new_open_cases_label, it.newOpenCases)
            newRecovered.text = getString(R.string.data_new_recovered_label, it.newRecovered)
        } ?: kotlin.run {
            activity?.onBackPressed()
        }

    }

    private fun DataDetailFragmentBinding.setupScreen() {
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

        searchButton.setOnClickListener {
            viewModel.getData()
        }
    }

    companion object {

        private const val DATA_KEY = "DATA_KEY"

        fun newInstance(
            placeData: PlaceDataBo
        ): CovidDataDetailFragment {
            val fragment = CovidDataDetailFragment()

            val args = Bundle()
            args.putSerializable(DATA_KEY, placeData)
            fragment.arguments = args

            return fragment
        }
    }
}