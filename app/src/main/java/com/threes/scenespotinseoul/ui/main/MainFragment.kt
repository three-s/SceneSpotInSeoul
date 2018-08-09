package com.threes.scenespotinseoul.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.ui.main.MainViewModel.Companion.TYPE_EXACTLY
import com.threes.scenespotinseoul.ui.main.MainViewModel.Companion.TYPE_SIMILAR
import com.threes.scenespotinseoul.ui.main.adapter.MediaCategoryAdapter
import com.threes.scenespotinseoul.utilities.DIR_BOTTOM
import com.threes.scenespotinseoul.utilities.ItemOffsetDecoration
import com.threes.scenespotinseoul.utilities.OFFSET_NORMAL
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mediaCategoryAdapter: MediaCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initViews()
        viewModel.mediaCategory.observe(this, Observer {
            mediaCategoryAdapter.submitList(it)
        })
    }

    private fun initViews() {
        mediaCategoryAdapter = MediaCategoryAdapter()
        list_media_category.setHasFixedSize(true)
        list_media_category.addItemDecoration(ItemOffsetDecoration(DIR_BOTTOM, OFFSET_NORMAL))
        list_media_category.layoutManager = LinearLayoutManager(context)
        list_media_category.adapter = mediaCategoryAdapter

        view_search.setAutoCompleteData(this, viewModel.tagAutoCompleteData)
        view_search.editOnTextChangeListener = { keyword, _, _, _ ->
            viewModel.loadTagAutoComplete(keyword)
        }
        view_search.editActionListener = {
            viewModel.requestSearch(TYPE_SIMILAR, it)
        }
        view_search.autoCompleteSelectListener = {
            viewModel.requestSearch(TYPE_EXACTLY, it)
        }
    }
}