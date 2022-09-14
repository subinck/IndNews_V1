package com.subin.indnews_v1.model.entity

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProfileImage")
data class ProfileImageEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="UUID")
    var uuid:String,
    @ColumnInfo(name = "Image", typeAffinity =BLOB)
    var data: ByteArray? = null

    ){
}