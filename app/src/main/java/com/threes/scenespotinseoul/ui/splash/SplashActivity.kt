package com.threes.scenespotinseoul.ui.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.threes.scenespotinseoul.MainActivity
import com.threes.scenespotinseoul.data.AppDataRepository
import com.threes.scenespotinseoul.utilities.runOnDiskIO

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (checkFirstRun()) {
            initDatabase()
            sharedPref.edit().putBoolean(KEY_FIRST_RUN, false).apply()
            requestPermission()
        } else {
            navigateMain()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        navigateMain()
    }

    private fun initDatabase() {
        Log.d("AppDatabase", "Pre-Populate data from resources.")
        runOnDiskIO {
            AppDataRepository(this).populateFromResources()
        }
    }

    private fun checkFirstRun(): Boolean = sharedPref.getBoolean(KEY_FIRST_RUN, true)

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), RC_REQUEST_PERM)
    }

    private fun navigateMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        const val RC_REQUEST_PERM = 100
        const val KEY_FIRST_RUN = "first_run"
    }
}