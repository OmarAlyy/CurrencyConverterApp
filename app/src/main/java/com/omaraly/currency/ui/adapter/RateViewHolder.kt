package com.omaraly.currency.ui.adapter

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.omaraly.currency.databinding.ItemRatesBinding
import com.omaraly.currency.utils.hide
import com.omaraly.currency.utils.show

class RateViewHolder(private val binding: ItemRatesBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(
        rate: Map.Entry<String, Double>,
        onItemClicked: ((Map.Entry<String, Double>) -> Unit)?,
        tracker: SelectionTracker<Long>?
    ) {
        binding.tvKey.text = rate.key
        binding.tvValue.text = rate.value.toString()
        binding.root.setOnClickListener {
            if (onItemClicked != null)
                onItemClicked(rate)
        }


        if (tracker?.isSelected(adapterPosition.toLong()) == true) {
            binding.imgDone.show()

        } else {
            binding.imgDone.hide()

        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }


}
