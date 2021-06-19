package com.omaraly.currency.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConverterViewModelFactory(
    private val rates: HashMap<String, Double>
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ConverterViewModel(rates) as T
}