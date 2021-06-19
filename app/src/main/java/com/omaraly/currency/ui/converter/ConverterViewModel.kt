package com.omaraly.currency.ui.converter

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ConverterViewModel
constructor(
    val rates: HashMap<String, Double>
) : ViewModel() {
    val ratesSelected: MutableLiveData<HashMap<String, Double>> = MutableLiveData()
    val baseValue: MutableLiveData<String> = MutableLiveData("1")
    private var input: Int = 1

    init {
        ratesSelected.value = rates
    }


    fun onBaseChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        input = if (s.isNotEmpty() && s.isDigitsOnly()) {
            s.toString().toInt()
        } else
            1

        val ratesEdit: HashMap<String, Double> = HashMap()
        for (oneRate in rates.entries) {
            ratesEdit[oneRate.key] = oneRate.value * input
        }
        ratesSelected.value = ratesEdit

    }
}
