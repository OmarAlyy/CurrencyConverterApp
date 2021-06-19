package com.omaraly.currency.ui.converter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.omaraly.currency.R
import com.omaraly.currency.databinding.FragmentConverterBinding
import com.omaraly.currency.ui.adapter.RatesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_converter.*

@AndroidEntryPoint
class FragmentConverter : Fragment(R.layout.fragment_converter) {

    private val args: FragmentConverterArgs by navArgs()
    private lateinit var listAdapter: RatesListAdapter
    private val viewModel: ConverterViewModel by viewModels {
        ConverterViewModelFactory(args.HashMap?.hashMap!!)
    }
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentConverterBinding.bind(view)
        binding.viewModel = viewModel
        super.onViewCreated(view, savedInstanceState)
        listAdapter = RatesListAdapter(null)
        observeRates()
    }

    private fun observeRates() {
        viewModel.ratesSelected.observe(viewLifecycleOwner, { map ->
            rvRates.adapter = listAdapter
            listAdapter.submitList(map.entries.toList())
        })
    }
}