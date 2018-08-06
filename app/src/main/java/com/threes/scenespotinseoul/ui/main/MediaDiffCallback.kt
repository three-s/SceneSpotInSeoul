package com.threes.scenespotinseoul.ui.main

import android.support.v7.util.DiffUtil
import com.threes.scenespotinseoul.data.model.Media

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {

    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
        (oldItem.id == newItem.id)
}