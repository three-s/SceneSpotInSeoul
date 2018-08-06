package com.threes.scenespotinseoul.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.threes.scenespotinseoul.data.AppDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase = AppDatabase.getInstance(application)
}