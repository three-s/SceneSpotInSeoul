package com.threes.scenespotinseoul.ui.main.adapter

import android.support.v7.util.DiffUtil
import com.threes.scenespotinseoul.data.model.Tag

class TagDiffCallback : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag?, newItem: Tag?): Boolean = (oldItem?.id == newItem?.id)

    override fun areContentsTheSame(oldItem: Tag?, newItem: Tag?): Boolean = (oldItem?.id == newItem?.id)
}