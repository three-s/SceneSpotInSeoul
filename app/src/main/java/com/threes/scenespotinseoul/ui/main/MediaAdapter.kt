package com.threes.scenespotinseoul.ui.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.model.Media
import kotlinx.android.synthetic.main.item_media.view.*

class MediaAdapter(itemCallback: DiffUtil.ItemCallback<Media> = MediaDiffCallback()) :
    ListAdapter<Media, MediaAdapter.MediaViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder = MediaViewHolder(parent)

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        with(getItem(position)) {
//            holder.ivMedia.text = name
        }
    }

    inner class MediaViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_media, parent, false)
    ) {
        val ivMedia: ImageView = itemView.iv_media
    }
}