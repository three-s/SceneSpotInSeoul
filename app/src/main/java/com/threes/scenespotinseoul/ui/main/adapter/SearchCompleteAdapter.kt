package com.threes.scenespotinseoul.ui.main.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.model.Tag
import kotlinx.android.synthetic.main.item_search_complete.view.*

class SearchCompleteAdapter(private val listener: OnSearchCompleteItemListener?, itemCallback: DiffUtil.ItemCallback<Tag> = TagDiffCallback()) :
    ListAdapter<Tag, SearchCompleteAdapter.SearchCompleteViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCompleteViewHolder =
        SearchCompleteViewHolder(parent)

    override fun onBindViewHolder(holder: SearchCompleteViewHolder, position: Int) {
        with (getItem(position)) {
            holder.tvResult.text = name
            holder.itemView.setOnClickListener {
                listener?.onItemClicked(this)
            }
        }
    }

    inner class SearchCompleteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context!!)
            .inflate(R.layout.item_search_complete, parent, false)
    ) {
        val tvResult: TextView = itemView.tv_result
    }

    interface OnSearchCompleteItemListener {

        fun onItemClicked(tag: Tag)
    }
}