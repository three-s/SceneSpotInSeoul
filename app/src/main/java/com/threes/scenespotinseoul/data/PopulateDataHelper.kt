package com.threes.scenespotinseoul.data

import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.LocationTag
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.MediaTag
import com.threes.scenespotinseoul.data.model.Scene
import com.threes.scenespotinseoul.data.model.SceneTag
import com.threes.scenespotinseoul.data.model.Tag

object PopulateDataHelper {

    fun insertLocation(db: AppDatabase, location: Location, tags: List<String>) {
        val locationId = db.locationDao().insert(location)
        tags.forEach {
            val tagId = db.tagDao().insert(Tag(0, it))
            db.locationTagDao().insert(LocationTag(tagId.toInt(), locationId.toInt()))
        }
    }

    fun insertMedia(db: AppDatabase, media: Media, tags: List<String>) {
        val mediaId = db.mediaDao().insert(media)
        tags.forEach {
            val tagId = db.tagDao().insert(Tag(0, it))
            db.mediaTagDao().insert(MediaTag(tagId.toInt(), mediaId.toInt()))
        }
    }

    fun insertScene(db: AppDatabase, scene: Scene, tags: List<String>) {
        val sceneId = db.sceneDao().insert(scene)
        tags.forEach {
            val tagId = db.tagDao().insert(Tag(0, it))
            db.sceneTagDao().insert(SceneTag(tagId.toInt(), sceneId.toInt()))
        }
    }

    fun findLocationIdByName(db: AppDatabase, name: String): Int = db.locationDao().loadByName(name).id

    fun findMediaIdByName(db: AppDatabase, name: String): Int = db.mediaDao().loadByName(name).id
}