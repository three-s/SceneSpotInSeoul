package com.threes.scenespotinseoul.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.threes.scenespotinseoul.BuildConfig
import com.threes.scenespotinseoul.data.dao.LocationDao
import com.threes.scenespotinseoul.data.dao.LocationTagDao
import com.threes.scenespotinseoul.data.dao.MediaDao
import com.threes.scenespotinseoul.data.dao.MediaTagDao
import com.threes.scenespotinseoul.data.dao.SceneDao
import com.threes.scenespotinseoul.data.dao.TagDao
import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.LocationTag
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.MediaTag
import com.threes.scenespotinseoul.data.model.Scene
import com.threes.scenespotinseoul.data.model.Tag
import com.threes.scenespotinseoul.utilities.AppExecutors
import com.threes.scenespotinseoul.utilities.DATABASE_NAME

@Database(
    entities = [Location::class, LocationTag::class, Media::class, MediaTag::class, Scene::class, Tag::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    abstract fun locationTagDao(): LocationTagDao

    abstract fun mediaDao(): MediaDao

    abstract fun mediaTagDao(): MediaTagDao

    abstract fun sceneDao(): SceneDao

    abstract fun tagDao(): TagDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        AppExecutors().diskIO.execute {
                            with(getInstance(context)) {
                                if (BuildConfig.DEBUG) {
                                    clearAllTables()
                                }
                                locationDao().insertAll(DataRepository.populateLocationData())
                                mediaDao().insertAll(DataRepository.populateMediaData())
                                sceneDao().insertAll(DataRepository.populateSceneData())
                                tagDao().insertAll(DataRepository.populateTagData())
                                locationTagDao().insertAll(DataRepository.populateLocationTagData())
                                mediaTagDao().insertAll(DataRepository.populateMediaTagData())
                            }
                        }
                    }
                })
                .build()
        }
    }
}