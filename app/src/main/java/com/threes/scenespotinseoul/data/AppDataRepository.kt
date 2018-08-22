package com.threes.scenespotinseoul.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.threes.scenespotinseoul.R
import com.threes.scenespotinseoul.data.AppDataHelper.findLocationIdByName
import com.threes.scenespotinseoul.data.AppDataHelper.findMediaIdByName
import com.threes.scenespotinseoul.data.AppDataHelper.insertLocation
import com.threes.scenespotinseoul.data.AppDataHelper.insertMedia
import com.threes.scenespotinseoul.data.AppDataHelper.insertScene
import com.threes.scenespotinseoul.data.model.DataInfo
import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.Scene

class AppDataRepository(private var context: Context) {

    private var db: AppDatabase = AppDatabase.getInstance(context)

    fun populateFromResources() {
        val gson = Gson()
        val locationStream = context.resources.openRawResource(R.raw.data_location)
        writeLocation(gson, locationStream.bufferedReader().use { it.readText() })
        val mediaStream = context.resources.openRawResource(R.raw.data_media)
        writeMedia(gson, mediaStream.bufferedReader().use { it.readText() })
        val sceneStream = context.resources.openRawResource(R.raw.data_scene)
        writeScene(gson, sceneStream.bufferedReader().use { it.readText() })
        writeDataInfo()
    }

    fun populateFromRemote() {
        throw UnsupportedOperationException()
    }

    private fun writeLocation(gson: Gson, data: String) {
        val locations = gson.fromJson(data, Array<LocationWrapper>::class.java)
        locations.forEach {
            insertLocation(db, it.location, it.tags)
        }
    }

    private fun writeMedia(gson: Gson, data: String) {
        val media = gson.fromJson(data, Array<MediaWrapper>::class.java)
        media.forEach {
            insertMedia(db, it.media, it.tags)
        }
    }

    private fun writeScene(gson: Gson, data: String) {
        val scenes = gson.fromJson(data, Array<SceneWrapper>::class.java)
        scenes.forEach {
            val mediaId = findMediaIdByName(db, it.mediaName)
            val locationId = findLocationIdByName(db, it.locationName)
            insertScene(db, Scene(
                    mediaId = mediaId,
                    locationId = locationId,
                    desc = it.sceneContent.desc,
                    image = it.sceneContent.image
            ), it.tags)
        }
    }

    private fun writeDataInfo() {
        val info = db.dataInfoDao().load()
        if (info == null) {
            db.dataInfoDao().insert(DataInfo(updatedDate = System.currentTimeMillis()))
        } else {
            info.updatedDate = System.currentTimeMillis()
            db.dataInfoDao().update(info)
        }
    }

    data class LocationWrapper(@SerializedName("data") val location: Location, val tags: List<String>)

    data class MediaWrapper(@SerializedName("data") val media: Media, val tags: List<String>)

    data class SceneContent(val desc: String, val image: String)

    data class SceneWrapper(
        @SerializedName("data") val sceneContent: SceneContent,
        @SerializedName("related_location") val locationName: String,
        @SerializedName("related_media") val mediaName: String,
        val tags: List<String>
    )
}