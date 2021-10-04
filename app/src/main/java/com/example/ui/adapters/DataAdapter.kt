package com.example.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.databinding.DataListItemBinding
import com.example.databinding.TotalDataListItemBinding
import com.example.model.bo.CovidTotalDataBo
import com.example.model.bo.DataResponseBo
import com.example.model.bo.PlaceDataBo
import com.example.model.bo.RegionDataBo


class DataAdapter(private val listener: DataListListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<Any> = mutableListOf()

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun updateData(newData: List<Any>) {
        val diffCallback = DataListDiffCallback(this.data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.data.clear()
        this.data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (holder) {
            is DataViewHolder -> {
                if (item is PlaceDataBo) {
                    holder.bind(item)
                    holder.itemView.setOnClickListener { listener.onItemSelected(item) }
                }
            }
            is RegionDataViewHolder -> {
                if (item is RegionDataBo) {
                    holder.bind(item)
                    holder.itemView.setOnClickListener { listener.onRegionSelected(item) }
                }
            }
            is TotalDataViewHolder -> {
                if (item is CovidTotalDataBo) {
                    holder.bind(item)
                }
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            DATA_VIEW_TYPE -> DataViewHolder(
                DataListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            REGION_DATA_VIEW_TYPE -> RegionDataViewHolder(
                DataListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> TotalDataViewHolder(
                TotalDataListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun getItemViewType(position: Int): Int =
        when(data[position]) {
            is PlaceDataBo -> DATA_VIEW_TYPE
            is RegionDataBo -> REGION_DATA_VIEW_TYPE
            else -> TOTAL_DATA_VIEW_TYPE
        }

    class DataViewHolder(private val binding: DataListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceDataBo) {
            binding.name.text = item.name
            binding.confirmed.text = binding.root.context.getString(R.string.data_confirmed_label, item.confirmed)
            binding.death.text = binding.root.context.getString(R.string.data_death_label, item.deaths)
        }
    }

    class RegionDataViewHolder(private val binding: DataListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RegionDataBo) {
            binding.name.text = item.name
            binding.confirmed.text = binding.root.context.getString(R.string.data_confirmed_label, item.confirmed)
            binding.death.text = binding.root.context.getString(R.string.data_death_label, item.deaths)
        }
    }

    class TotalDataViewHolder(private val binding: TotalDataListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CovidTotalDataBo) {
            binding.confirmed.text = binding.root.context.getString(R.string.total_data_confirmed_label, item.confirmed)
            binding.death.text = binding.root.context.getString(R.string.total_data_death_label, item.deaths)
        }
    }

    class DataListDiffCallback(private val oldList: List<Any>, private val newList: List<Any>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    companion object {
        const val DATA_VIEW_TYPE = 0
        const val TOTAL_DATA_VIEW_TYPE = 1
        const val REGION_DATA_VIEW_TYPE = 2
    }

}

interface DataListListener {
    fun onItemSelected(item: PlaceDataBo)
    fun onRegionSelected(region: RegionDataBo)
}
