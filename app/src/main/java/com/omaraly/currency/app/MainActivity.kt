package com.omaraly.currency.app


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.omaraly.currency.R
import com.omaraly.currency.utils.ConnectivityLiveData
import com.omaraly.currency.app.Constants.ANIMATION_DURATION
import com.omaraly.currency.utils.getColorRes
import com.omaraly.currency.utils.hide
import com.omaraly.currency.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
     }

    override fun onResume() {
        handleNetworkChanges()

        super.onResume()
    }

    private fun handleNetworkChanges() {

        val connectivityLiveData = ConnectivityLiveData(application)
          connectivityLiveData.observe(this, { isAvailable ->
            if (!isAvailable) {
                textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                textViewNetworkStatus.text = getString(R.string.text_connectivity)
                networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
     }




    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}