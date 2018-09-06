package com.threes.scenespotinseoul.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringDef
import com.threes.scenespotinseoul.data.AppDatabase
import com.threes.scenespotinseoul.ui.main.adapter.MediaCategory
import com.threes.scenespotinseoul.utilities.runOnDiskIO
import com.threes.scenespotinseoul.utilities.runOnMain

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase = AppDatabase.getInstance(application)

    private val _mediaCategoryData = MutableLiveData<List<MediaCategory>>()

    val mediaCategory: LiveData<List<MediaCategory>>
        get() = _mediaCategoryData

    init {
        loadMediaCategory()
    }

    fun loadMediaCategory() {
        runOnDiskIO {
            val media = db.mediaDao().loadAll()
            runOnMain {
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
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        TYPE_SIMILAR,
        TYPE_EXACTLY
    )
    annotation class RequestType

    companion object {
        const val TYPE_SIMILAR = "type_similar"
        const val TYPE_EXACTLY = "type_exactly"
    }
}