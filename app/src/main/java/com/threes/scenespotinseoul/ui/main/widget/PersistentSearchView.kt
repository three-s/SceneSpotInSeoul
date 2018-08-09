package com.threes.scenespotinseoul.ui.main.widget

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
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

    var autoCompleteSelectListener: ((String) -> Unit)? = null
    var editActionListener: ((String) -> Unit)? = null
    var editBeforeTextChangeListener: ((String, Int, Int, Int) -> Unit)? = null
    var editOnTextChangeListener: ((String, Int, Int, Int) -> Unit)? = null
    var editAfterTextChangeListener: ((Editable?) -> Unit)? = null

    init {
        View.inflate(context, R.layout.persistent_search_view, this)

        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2F, resources.displayMetrics)
        useCompatPadding = true

        autoCompleteAdapter = SearchAutoCompleteAdapter()
        autoCompleteAdapter.itemSelectListener = {
            edit_search.setText(it)
            collapseSearchView()
            hideKeyboard()
            autoCompleteSelectListener?.invoke(it)
        }

        list_search_autocomplete.layoutManager = LinearLayoutManager(context)
        list_search_autocomplete.adapter = autoCompleteAdapter

        edit_search.addTextChangedListener(object : TextWatcher {
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
        })

        edit_search.setOnEditorActionListener { view, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    collapseSearchView()
                    hideKeyboard()
                    editActionListener?.invoke(view.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    fun setAutoCompleteData(lifeCycleOwner: LifecycleOwner, data: LiveData<List<String>>) {
        data.observe(lifeCycleOwner, Observer {
            autoCompleteAdapter.submitList(it!!)
        })
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
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}