package com.sano.spxdataclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sano.spacexlaunches.R
import com.sano.spxdataclient.launch.LaunchesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, LaunchesFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) finish()
        super.onBackPressed()
    }
}
