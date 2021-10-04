package com.example.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.DateUtils
import com.example.R
import com.example.databinding.DataDetailFragmentBinding
import com.example.di.ViewModelFactory
import com.example.model.bo.DataResponseBo
import com.example.model.bo.PlaceDataBo
import com.example.ui.viewmodels.DataListViewModel
import com.google.android.material.tabs.TabLayout
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
        configureObservers()
        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.placeSelectedId = null
        binding = null
    }

    private fun configureObservers() {
        viewModel.dataLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding?.updateData(result)
        })
    }

    private fun DataDetailFragmentBinding.updateData(dataResponse: DataResponseBo) {
        if (viewModel.dataMode == DataListViewModel.DataMode.WORLD_DATA) {
            showCountryData(dataResponse)
        } else {
            showRegionData(dataResponse)
        }

    }

    private fun DataDetailFragmentBinding.showCountryData(dataResponse: DataResponseBo) {
        dataResponse.placesData.firstOrNull { viewModel.placeSelectedId == it.id }?.let {
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

    private fun DataDetailFragmentBinding.showRegionData(dataResponse: DataResponseBo) {
        dataResponse.placesData.firstOrNull()?.regions?.firstOrNull { viewModel.placeSelectedId == it.id }?.let {
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