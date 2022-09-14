package com.subin.indnews_v1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.subin.indnews_v1.model.CategoryItems
import com.subin.indnews_v1.model.entity.ProfileImageEntity

@Database(entities = [CategoryItems::class,ProfileImageEntity::class], version = 1, exportSchema = false)
abstract class IndNewsDatabase:RoomDatabase() {
    abstract fun providesDAO():IndNewsDAO
}