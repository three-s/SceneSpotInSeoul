package com.threes.scenespotinseoul.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.threes.scenespotinseoul.data.AppDatabase
import com.threes.scenespotinseoul.data.model.Tag
import com.threes.scenespotinseoul.ui.main.adapter.MediaCategory
import com.threes.scenespotinseoul.utilities.AppExecutors

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase = AppDatabase.getInstance(application)
    private var executors: AppExecutors = AppExecutors()

    private val _tagAutoCompleteData = MutableLiveData<List<Tag>>()
    private val _mediaCategoryData = MutableLiveData<List<MediaCategory>>()

    val tagAutoCompleteData: LiveData<List<Tag>>
        get() = _tagAutoCompleteData

    val mediaCategory: LiveData<List<MediaCategory>>
        get() = _mediaCategoryData

    init {
        loadMediaCategory()
    }

    fun loadMediaCategory() {
        executors.diskIO().execute {
            val media = db.mediaDao().loadAll()
            executors.mainThread().execute {
                _mediaCategoryData.value = listOf(
                    MediaCategory("Category 1", media),
                    MediaCategory("Category 2", media),
                    MediaCategory("Category 3", media),
                    MediaCategory("Category 4", media),
                    MediaCategory("Category 5", media),
                    MediaCategory("Category 6", media),
                    MediaCategory("Category 7", media)
                )
            }
        }
    }

    fun loadTagAutoComplete(keyword: String) {
        if (keyword.isEmpty()) {
            _tagAutoCompleteData.value = listOf()
        } else {
            executors.diskIO().execute {
                val tags = db.tagDao().loadBySimilarName("%$keyword%")
                executors.mainThread().execute {
                    _tagAutoCompleteData.value = tags
                }
            }
        }
    }
}