package com.threes.scenespotinseoul.data

import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.LocationTag
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.MediaTag
import com.threes.scenespotinseoul.data.model.Scene
import com.threes.scenespotinseoul.data.model.SceneTag
import com.threes.scenespotinseoul.data.model.Tag

object AppDataHelper {

    fun insertLocation(db: AppDatabase, location: Location, tags: List<String>) {
        val locationRowId = db.locationDao().insert(location)
        val locationId = db.locationDao().loadByRowId(locationRowId).id
        tags.forEach {
            val tag = db.tagDao().loadByName(it)
            val tagId =
                if (tag != null) tag.id
                else {
                    val tagRowId = db.tagDao().insert(Tag(0, it))
                    db.tagDao().loadByRowId(tagRowId).id
                }
            db.locationTagDao().insert(LocationTag(tagId, locationId))
        }
    }

    fun insertMedia(db: AppDatabase, media: Media, tags: List<String>) {
        val mediaRowId = db.mediaDao().insert(media)
        val mediaId = db.mediaDao().loadByRowId(mediaRowId).id
        tags.forEach {
            val tag = db.tagDao().loadByName(it)
            val tagId =
                if (tag != null) {
                    tag.id
                } else {
                    val tagRowId = db.tagDao().insert(Tag(0, it))
                    db.tagDao().loadByRowId(tagRowId).id
                }
            db.mediaTagDao().insert(MediaTag(tagId, mediaId))
        }
    }

    fun insertScene(db: AppDatabase, scene: Scene, tags: List<String>) {
        val sceneRowId = db.sceneDao().insert(scene)
        val sceneId = db.sceneDao().loadByRowId(sceneRowId).id
        tags.forEach {
            val tag = db.tagDao().loadByName(it)
            val tagId =
                if (tag != null) {
                    tag.id
                } else {
                    val tagRowId = db.tagDao().insert(Tag(0, it))
                    db.tagDao().loadByRowId(tagRowId).id
                }
            db.sceneTagDao().insert(SceneTag(tagId, sceneId))
        }
    }

    fun findLocationIdByName(db: AppDatabase, name: String): Int = db.locationDao().loadByName(name).id

    fun findMediaIdByName(db: AppDatabase, name: String): Int = db.mediaDao().loadByName(name).id
}