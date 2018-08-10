package com.threes.scenespotinseoul.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.Scene
import kotlinx.android.synthetic.main.item_media.view.*

class SearchResultAdapter<T>(private val items: List<T>) :
    RecyclerView.Adapter<SearchResultAdapter<T>.SearchResultViewHolder>() {

    var itemSelectListener: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder =
        SearchResultViewHolder(parent)

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        with(holder) {
            var url: String? = null
            val item = items[position]
            when (item) {
                is Media -> url = item.image
                is Scene -> url = item.image
                is Location -> url = item.image
            }
            Glide.with(itemView)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(ivMedia)

            itemView.setOnClickListener {
                itemSelectListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class SearchResultViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context!!)
            .inflate(R.layout.item_media, parent, false)
    ) {
        val ivMedia: ImageView = itemView.iv_media
    }
}