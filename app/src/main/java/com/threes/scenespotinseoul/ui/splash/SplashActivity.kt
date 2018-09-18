package com.threes.scenespotinseoul.ui.splash

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.threes.scenespotinseoul.MainActivity
import com.threes.scenespotinseoul.workers.SyncDatabaseWorker

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (checkFirstRun()) {
            requestPermission()
            sharedPref.edit().putBoolean(KEY_FIRST_RUN, false).apply()
        } else {
            initDatabase()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        initDatabase()
    }

    private fun initDatabase() {
        val syncDatabaseWork = OneTimeWorkRequest.Builder(SyncDatabaseWorker::class.java).build()
        WorkManager.getInstance().enqueue(syncDatabaseWork)
        WorkManager.getInstance().getStatusById(syncDatabaseWork.id).observe(this, Observer {
            if (it != null && it.state.isFinished) {
                navigateMain()
            }
        })
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