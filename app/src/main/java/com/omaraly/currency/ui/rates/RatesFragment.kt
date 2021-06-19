package com.omaraly.currency.ui.rates

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.omaraly.currency.R
import com.omaraly.currency.model.HashMapToPass
import com.omaraly.currency.model.Resource
import com.omaraly.currency.ui.adapter.RatesListAdapter
import com.omaraly.currency.ui.adapter.RatesLookup
import com.omaraly.currency.utils.hide
import com.omaraly.currency.utils.show
import com.omaraly.currency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_rates.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RatesFragment : Fragment(R.layout.fragment_rates), (Map.Entry<String, Double>) -> Unit {

    private lateinit var listAdapter: RatesListAdapter
    private var tracker: SelectionTracker<Long>? = null
    private val viewModel: RatesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            (Dispatchers.IO)
            viewModel.getRates()
        }
        listAdapter = RatesListAdapter(this)
        observeRates()
        onViewsActions()

    }

    private fun onViewsActions() {
        btnSelected.setOnClickListener {
            val rates: HashMap<String, Double> = HashMap()
            for (positionSelect in tracker?.selection!!) {
                val item = listAdapter.currentList[positionSelect.toInt()]
                rates[item.key] = item.value
            }
            goToConverter(rates)
        }
        darkMode.setOnClickListener {
            val mode =
                if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                    Configuration.UI_MODE_NIGHT_NO
                ) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getRates()
        }

    }

    private fun observeSelectionTracker() {
        tracker = SelectionTracker.Builder(
            "selection-1",
            rvRates,
            StableIdKeyProvider(rvRates),
            RatesLookup(rvRates),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()
        listAdapter.setTracker(tracker!!)
        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                val nItems: Int? = tracker?.selection?.size()
                if (nItems != null && nItems > 0) {
                    btnSelected.apply {
                        text = "$nItems items selected"
                        show()
                    }

                } else {
                    btnSelected.apply {
                        text = ""
                        hide()
                    }

                }

            }
        })
    }

    private fun observeRates() {
        viewModel.currencyResponse.observe(
            viewLifecycleOwner,
            { response ->
                when (response) {
                    is Resource.Success -> {
                        showLoading(false)
                        response.data?.let {
                            rvRates.adapter = listAdapter
                            listAdapter.submitList(it.rates.entries.toList())
                            lastUpdates.text = "Last update: ${it.date}"
                            observeSelectionTracker()
                        }
                    }

                    is Resource.Error -> {
                        showLoading(false)
                        response.message?.let {
                            context?.showToast(getString(R.string.text_no_internet))
                        }
                    }
                    is Resource.Loading -> {
                        showLoading(true)

                    }
                }
            })
    }

    override fun invoke(item: Map.Entry<String, Double>) {
        val rates: HashMap<String, Double> = HashMap()
        rates[item.key] = item.value
        goToConverter(rates)
    }

    private fun showLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }


    private fun goToConverter(rates: HashMap<String, Double>) {
        val bundle = Bundle().apply {
            putSerializable("HashMap", HashMapToPass(rates))
        }
        findNavController()
            .navigate(
                R.id.action_ratesFragment_to_fragmentConverter,
                bundle
            )

    }

}