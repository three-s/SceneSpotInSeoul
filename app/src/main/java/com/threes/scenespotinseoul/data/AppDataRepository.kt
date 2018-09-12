package com.threes.scenespotinseoul.data

import android.content.Context
import com.google.gson.Gson
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.AppDataHelper.findLocationIdByName
import com.threes.scenespotinseoul.data.AppDataHelper.findMediaIdByName
import com.threes.scenespotinseoul.data.AppDataHelper.insertLocation
import com.threes.scenespotinseoul.data.AppDataHelper.insertMedia
import com.threes.scenespotinseoul.data.AppDataHelper.insertScene
import com.threes.scenespotinseoul.data.model.DataInfo
import com.threes.scenespotinseoul.data.model.Scene
import com.threes.scenespotinseoul.data.remote.model.LocationWrapper
import com.threes.scenespotinseoul.data.remote.model.MediaWrapper
import com.threes.scenespotinseoul.data.remote.model.SceneWrapper
import com.threes.scenespotinseoul.utilities.LOCATION_TABLE
import com.threes.scenespotinseoul.utilities.MEDIA_TABLE
import com.threes.scenespotinseoul.utilities.SCENE_TABLE

class AppDataRepository(private var context: Context) {

    private var db: AppDatabase = AppDatabase.getInstance(context)

    fun populateFromResources() {
        val gson = Gson()
        // Write location
        val locationStream = context.resources.openRawResource(R.raw.data_location)
        val locationString = locationStream.bufferedReader().use { it.readText() }
        val locationWrappers = gson.fromJson(locationString, Array<LocationWrapper>::class.java)
        writeLocations(locationWrappers.toList())
        // Write media
        val mediaStream = context.resources.openRawResource(R.raw.data_media)
        val mediaString = mediaStream.bufferedReader().use { it.readText() }
        val mediaWrappers = gson.fromJson(mediaString, Array<MediaWrapper>::class.java)
        writeMedia(mediaWrappers.toList())
        // Write scenes
        val sceneStream = context.resources.openRawResource(R.raw.data_scene)
        val sceneString = sceneStream.bufferedReader().use { it.readText() }
        val scenesWrappers = gson.fromJson(sceneString, Array<SceneWrapper>::class.java)
        writeScenes(scenesWrappers.toList())
    }

    private fun writeLocations(locationWrappers: List<LocationWrapper>) {
        locationWrappers.forEach {
            insertLocation(db, it.location, it.tags)
        }
        writeDataInfo(LOCATION_TABLE)
    }

    private fun writeMedia(mediaWrappers: List<MediaWrapper>) {
        mediaWrappers.forEach {
            insertMedia(db, it.media, it.tags)
        }
        writeDataInfo(MEDIA_TABLE)
    }

    private fun writeScenes(sceneWrappers: List<SceneWrapper>) {
        sceneWrappers.forEach {
            val mediaId = findMediaIdByName(db, it.mediaName)
            val locationId = findLocationIdByName(db, it.locationName)
            insertScene(db, Scene(
                    mediaId = mediaId,
                    locationId = locationId,
                    desc = it.sceneContent.desc,
                    image = it.sceneContent.image
            ), it.tags)
        }
        writeDataInfo(SCENE_TABLE)
    }

    private fun writeDataInfo(tableName: String) {
        val info = db.dataInfoDao().load(tableName)
        if (info == null) {
            db.dataInfoDao().insert(DataInfo(tableName, updatedDate = System.currentTimeMillis()))
        } else {
            info.updatedDate = System.currentTimeMillis()
            db.dataInfoDao().update(info)
        }
    }
}