package com.omaraly.currency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.omaraly.currency.databinding.ItemRatesBinding

class RatesListAdapter(
    private val onItemClicked: ((Map.Entry<String, Double>) -> Unit)? ,
    private var tracker: SelectionTracker<Long>? = null
) : ListAdapter<Map.Entry<String, Double>, RateViewHolder>(DIFF_CALLBACK) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RateViewHolder(
        ItemRatesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: RateViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked, tracker)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Map.Entry<String, Double>>() {
            override fun areItemsTheSame(
                oldItem: Map.Entry<String, Double>,
                newItem: Map.Entry<String, Double>
            ): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(
                oldItem: Map.Entry<String, Double>,
                newItem: Map.Entry<String, Double>
            ): Boolean =
                oldItem == newItem
        }
    }


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position


    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }


}
