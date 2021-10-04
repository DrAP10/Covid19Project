package com.example.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.ui.dialogs.DatePickerFragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun showDatePickerDialog(
        listener: DatePickerDialog.OnDateSetListener,
    ) {
        val newFragment = DatePickerFragment(listener)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    fun getDatePickerListener(action: (year: Int, month: Int, day: Int) -> Unit): DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            action(year, month, day)
        }

}