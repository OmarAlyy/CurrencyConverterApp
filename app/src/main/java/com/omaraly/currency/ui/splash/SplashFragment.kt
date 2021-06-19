package com.omaraly.currency.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.omaraly.currency.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                findNavController()
                    .navigate(
                        R.id.action_splashFragment_to_ratesFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.splashFragment,
                                true
                            ).build()
                    )
            }
        }
        super.onResume()
    }


}