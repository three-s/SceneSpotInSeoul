package com.threes.scenespotinseoul.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.model.Tag
import kotlinx.android.synthetic.main.item_search_complete.view.*

class SearchAutoCompleteAdapter(private var tags: List<Tag> = listOf()) :
    RecyclerView.Adapter<SearchAutoCompleteAdapter.SearchCompleteViewHolder>() {
    lateinit var itemSelectListener: (Tag) -> Unit?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCompleteViewHolder =
        SearchCompleteViewHolder(parent)

    override fun onBindViewHolder(holder: SearchCompleteViewHolder, position: Int) {
        with(tags[position]) {
            holder.tvResult.text = name
            holder.itemView.setOnClickListener {
                itemSelectListener(this)
            }
        }
    }

    override fun getItemCount(): Int = tags.size

    fun submitList(newTags: List<Tag>) {
        tags = newTags
        notifyDataSetChanged()
    }

    inner class SearchCompleteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context!!)
            .inflate(R.layout.item_search_complete, parent, false)
    ) {
        val tvResult: TextView = itemView.tv_result
    }
}