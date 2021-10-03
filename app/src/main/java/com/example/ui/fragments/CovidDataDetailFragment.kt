package com.example.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import com.example.databinding.DataDetailFragmentBinding
import com.example.model.bo.PlaceDataBo


class CovidDataDetailFragment : BaseFragment() {

    private var binding: DataDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataDetailFragmentBinding.inflate(inflater, container, false)
        binding?.setupScreen()
        return binding?.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = getString(R.string.app_name)
        binding = null
    }

    private fun DataDetailFragmentBinding.setupScreen() {

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