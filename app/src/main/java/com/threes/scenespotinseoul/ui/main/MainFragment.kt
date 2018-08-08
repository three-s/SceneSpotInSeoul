package com.threes.scenespotinseoul.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.ui.main.adapter.MediaCategoryAdapter
import com.threes.scenespotinseoul.ui.main.adapter.SearchAutoCompleteAdapter
import com.threes.scenespotinseoul.utilities.DIR_BOTTOM
import com.threes.scenespotinseoul.utilities.ItemOffsetDecoration
import com.threes.scenespotinseoul.utilities.OFFSET_NORMAL
import com.threes.scenespotinseoul.utilities.TYPE_EXACTLY
import com.threes.scenespotinseoul.utilities.TYPE_SIMILAR
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mediaCategoryAdapter: MediaCategoryAdapter
    private lateinit var searchAutoCompleteAdapter: SearchAutoCompleteAdapter

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
            if (it?.isEmpty()!!) {
                collapseSearchView()
            }
            searchAutoCompleteAdapter.submitList(it)
        })
    }

    private fun initViews() {
        mediaCategoryAdapter = MediaCategoryAdapter()
        list_media_category.setHasFixedSize(true)
        list_media_category.addItemDecoration(ItemOffsetDecoration(DIR_BOTTOM, OFFSET_NORMAL))
        list_media_category.layoutManager = LinearLayoutManager(context)
        list_media_category.adapter = mediaCategoryAdapter

        searchAutoCompleteAdapter = SearchAutoCompleteAdapter()
        searchAutoCompleteAdapter.itemSelectListener = {
            edit_search.setText(it.name)
            collapseSearchView()
            hideKeyboard()
            viewModel.requestSearch(TYPE_EXACTLY, it.name)
        }
        list_search_autocomplete.layoutManager = LinearLayoutManager(context)
        list_search_autocomplete.adapter = searchAutoCompleteAdapter

        edit_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
                expandSearchView()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.loadTagAutoComplete(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No-op
            }
        })

        edit_search.setOnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    collapseSearchView()
                    hideKeyboard()
                    viewModel.requestSearch(TYPE_SIMILAR, view.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun expandSearchView() {
        TransitionManager.beginDelayedTransition(layout_search, ChangeBounds())
        val set = ConstraintSet()
        set.clone(layout_search)
        set.setVisibility(list_search_autocomplete.id, ConstraintSet.VISIBLE)
        set.applyTo(layout_search)
    }

    private fun collapseSearchView() {
        TransitionManager.beginDelayedTransition(layout_search, ChangeBounds())
        val set = ConstraintSet()
        set.clone(layout_search)
        set.setVisibility(list_search_autocomplete.id, ConstraintSet.GONE)
        set.applyTo(layout_search)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}