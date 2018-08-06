package com.threes.scenespotinseoul.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.AppDatabase
import com.threes.scenespotinseoul.utilities.AppExecutors
import com.threes.scenespotinseoul.utilities.DIR_BOTTOM
import com.threes.scenespotinseoul.utilities.ItemOffsetDecoration
import com.threes.scenespotinseoul.utilities.OFFSET_NORMAL
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MediaCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initViews()

        val db = AppDatabase.getInstance(context!!)
        val executors = AppExecutors()
        executors.diskIO().execute {
            val media = db.mediaDao().loadAll()
            executors.mainThread().execute {
                adapter.submitList(
                    listOf(
                        MediaCategory("Category 1", media),
                        MediaCategory("Category 2", media),
                        MediaCategory("Category 3", media),
                        MediaCategory("Category 4", media),
                        MediaCategory("Category 5", media),
                        MediaCategory("Category 6", media),
                        MediaCategory("Category 7", media)
                    )
                )
            }
        }
    }

    private fun initViews() {
        adapter = MediaCategoryAdapter()
        list_media_category.setHasFixedSize(true)
        list_media_category.addItemDecoration(ItemOffsetDecoration(DIR_BOTTOM, OFFSET_NORMAL))
        list_media_category.layoutManager = LinearLayoutManager(context)
        list_media_category.adapter = adapter
    }
}