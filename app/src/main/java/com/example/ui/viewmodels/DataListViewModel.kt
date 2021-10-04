package com.example.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DateUtils
import com.example.model.bo.DataResponseBo
import com.example.repository.CovidRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DataListViewModel @Inject constructor(
    private val repository: CovidRepository
) : ViewModel() {

    private val mutableDataLiveData: MutableLiveData<DataResponseBo> = MutableLiveData()
    val dataLiveData: LiveData<DataResponseBo> = mutableDataLiveData

    var dateFrom: Date = Date()
    var dateTo: Date? = Date()
    var allowDateRange: Boolean = false
    var placeSelectedId: String? = null
    var dataMode: DataMode = DataMode.WORLD_DATA

    fun getData() = viewModelScope.launch {
        mutableDataLiveData.postValue(
            if (dataMode == DataMode.WORLD_DATA) {
                repository.getWorldDataByDateRange(dateFrom, if (allowDateRange) dateTo else null)
            } else {
                repository.getCountryDataByDateRange(
                    DataMode.SPAIN_DATA.id,
                    dateFrom,
                    if (allowDateRange) dateTo else null
                )
            }
        )
    }

    fun generateScreenTitle(): String {
        val dateText = if (allowDateRange) {
            "${DateUtils.getApiDateStringFormatted(dateFrom)} to ${DateUtils.getApiDateStringFormatted(dateTo)}"
        } else {
            "on ${DateUtils.getApiDateStringFormatted(dateFrom)}"
        }
        return "${dataMode.getName()} $dateText"
    }

    enum class DataMode(val id: String) {
        WORLD_DATA(""),
        SPAIN_DATA("spain");

        fun getName(): String =
            when(this) {
                WORLD_DATA -> "World"
                SPAIN_DATA -> "Spain"
            }
    }
}