package com.subin.indnews_v1.Utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.subin.indnews_v1.model.Category
import com.subin.indnews_v1.model.LiveChannels
import com.subin.indnews_v1.model.StateList
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("EEEE , dd-MMMM-yyyy")
        val currentDate = sdf.format(Date())
       return currentDate
    }

    fun loadJSONFromAsset(context: Context, fileName: String?): String? {
        var json = ""
        json = try {
            val isP = context.assets.open(fileName!!)
            val size = isP.available()
            val buffer = ByteArray(size)
            isP.read(buffer)
            isP.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getCategory(context:Context):Category{
        val category:String= loadJSONFromAsset(context=context, fileName = "Category.json")!!
        val gson:Gson= Gson()
        return gson.fromJson(category,Category::class.java)

    }

    fun getStateList(context:Context):StateList{
        val category:String= loadJSONFromAsset(context=context, fileName = "StateList.json")!!
        val gson:Gson= Gson()
        return gson.fromJson(category,StateList::class.java)

    }

    fun getChannels(context:Context):LiveChannels{
        val channels:String= loadJSONFromAsset(context=context, fileName = "LiveChannels.json")!!
        val gson:Gson= Gson()
        return gson.fromJson(channels,LiveChannels::class.java)

    }

    fun bitmapToByteArray(bitmap: Bitmap):ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteArray = stream.toByteArray()
        return byteArray
    }

    fun byteArrayToBitmap( byteArray: ByteArray):Bitmap
    {
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0,byteArray.size)
        return bmp
    }
}