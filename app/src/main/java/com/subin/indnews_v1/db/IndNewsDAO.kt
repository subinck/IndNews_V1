package com.subin.indnews_v1.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.subin.indnews_v1.model.CategoryItems
import com.subin.indnews_v1.model.entity.ProfileImageEntity

@Dao
interface IndNewsDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insertCategoryDetails(category:List<CategoryItems>):List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insertProfileImage(profileImage:ProfileImageEntity)

    @Query("SELECT * FROM profileimage where UUID=:uuid" )
    suspend fun getProfileImage(uuid:String): ProfileImageEntity


}