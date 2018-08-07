package com.threes.scenespotinseoul.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.model.Tag
import com.threes.scenespotinseoul.ui.main.adapter.MediaCategoryAdapter
import com.threes.scenespotinseoul.ui.main.adapter.SearchCompleteAdapter
import com.threes.scenespotinseoul.utilities.DIR_BOTTOM
import com.threes.scenespotinseoul.utilities.ItemOffsetDecoration
import com.threes.scenespotinseoul.utilities.OFFSET_NORMAL
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {

        val TAG = MainFragment::class.java.simpleName
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mediaCategoryAdapter: MediaCategoryAdapter
    private lateinit var searchCompleteAdapter: SearchCompleteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.mediaCategory.observe(this, Observer {
            mediaCategoryAdapter.submitList(it)
        })
        viewModel.tagAutoCompleteData.observe(this, Observer {
            searchCompleteAdapter.submitList(it)
        })
    }

    private fun initViews() {
        mediaCategoryAdapter = MediaCategoryAdapter()
        list_media_category.setHasFixedSize(true)
        list_media_category.addItemDecoration(ItemOffsetDecoration(DIR_BOTTOM, OFFSET_NORMAL))
        list_media_category.layoutManager = LinearLayoutManager(context)
        list_media_category.adapter = mediaCategoryAdapter

        searchCompleteAdapter = SearchCompleteAdapter(object : SearchCompleteAdapter.OnSearchCompleteItemListener {
            override fun onItemClicked(tag: Tag) {
                edit_search.setText(tag.name)
                list_search_complete.visibility = View.GONE
            }
        })
        list_search_complete.layoutManager = LinearLayoutManager(context)
        list_search_complete.adapter = searchCompleteAdapter

        edit_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.loadTagAutoComplete(s.toString())
                list_search_complete.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // No-op
            }
        })
    }
}