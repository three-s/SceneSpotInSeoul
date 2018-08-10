package com.threes.scenespotinseoul.ui.main.widget

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.threes.scenespotinseoul.R
import kotlinx.android.synthetic.main.persistent_search_view.view.*

class PersistentSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var autoCompleteAdapter: SearchAutoCompleteAdapter
    private var searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            expandSearchView()
            editBeforeTextChangeListener?.invoke(s.toString(), start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            editOnTextChangeListener?.invoke(s.toString(), start, before, count)
        }

        override fun afterTextChanged(s: Editable?) {
            editAfterTextChangeListener?.invoke(s)
        }
    }

    var autoCompleteSelectListener: ((String) -> Unit)? = null
    var editActionListener: ((String) -> Unit)? = null
    var editBeforeTextChangeListener: ((String, Int, Int, Int) -> Unit)? = null
    var editOnTextChangeListener: ((String, Int, Int, Int) -> Unit)? = null
    var editAfterTextChangeListener: ((Editable?) -> Unit)? = null
    var backButtonClickListener: ((View) -> Unit)? = null

    init {
        View.inflate(context, R.layout.persistent_search_view, this)

        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2F, resources.displayMetrics)
        useCompatPadding = true

        autoCompleteAdapter = SearchAutoCompleteAdapter()
        autoCompleteAdapter.itemSelectListener = {
            setAutoCompletedText(it)
            collapseSearchView()
            clearAutoComplete()
            hideKeyboard()
            autoCompleteSelectListener?.invoke(it)
        }

        list_search_autocomplete.layoutManager = LinearLayoutManager(context)
        list_search_autocomplete.adapter = autoCompleteAdapter

        edit_search.addTextChangedListener(searchTextWatcher)

        edit_search.setOnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    collapseSearchView()
                    clearAutoComplete()
                    hideKeyboard()
                    editActionListener?.invoke(view.text.toString())
                    true
                }
                else -> false
            }
        }

        btn_back.setOnClickListener {
            backButtonClickListener?.invoke(it)
        }
    }

    fun showBackButton() {
        TransitionManager.beginDelayedTransition(layout_search)
        val set = ConstraintSet()
        set.clone(layout_search)
        set.setVisibility(iv_search_icon.id, ConstraintSet.INVISIBLE)
        set.setVisibility(btn_back.id, ConstraintSet.VISIBLE)
        set.applyTo(layout_search)
    }

    fun hideBackButton() {
        TransitionManager.beginDelayedTransition(layout_search)
        val set = ConstraintSet()
        set.clone(layout_search)
        set.setVisibility(iv_search_icon.id, ConstraintSet.VISIBLE)
        set.setVisibility(btn_back.id, ConstraintSet.INVISIBLE)
        set.applyTo(layout_search)
        edit_search.setText("")
    }

    fun setAutoCompleteData(lifeCycleOwner: LifecycleOwner, data: LiveData<List<String>>) {
        data.observe(lifeCycleOwner, Observer {
            autoCompleteAdapter.submitList(it!!)
        })
    }

    private fun setAutoCompletedText(autoCompletedText: String) {
        edit_search.removeTextChangedListener(searchTextWatcher)
        edit_search.setText(autoCompletedText)
        edit_search.addTextChangedListener(searchTextWatcher)
    }

    private fun clearAutoComplete() {
        autoCompleteAdapter.submitList(listOf())
    }

    private fun expandSearchView() {
        list_search_autocomplete.visibility = VISIBLE
    }

    private fun collapseSearchView() {
        list_search_autocomplete.visibility = GONE
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}