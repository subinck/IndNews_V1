package com.subin.indnews_v1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryItems")
data class CategoryItems (@ColumnInfo(name = "Name")
                          val name:String,
                          @ColumnInfo(name = "Image")
                          val image:String,
                          @ColumnInfo(name = "isSelected")
                          var isSelected:Boolean=false){
       @PrimaryKey(autoGenerate = true)     var id:Int?=null }
