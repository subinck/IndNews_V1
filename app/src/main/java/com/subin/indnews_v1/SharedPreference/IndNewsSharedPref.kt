package com.subin.indnews_v1.SharedPreference

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.subin.indnews_v1.Constants.PrefConstants.IS_LOGIN
import com.subin.indnews_v1.model.UserDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


interface AppPreference {
    var isLogin:Boolean
    fun clearData()

}

class IndNewsSharedPref @Inject constructor(
    @ApplicationContext context: Context,

    ):AppPreference {



    private var preference = context.getSharedPreferences("dagger-pref", Context.MODE_PRIVATE)
    private var editor = preference.edit()

    override var isLogin: Boolean
        get() = getIsLogin(IS_LOGIN)
        set(value) {
            setIsLogin(IS_LOGIN, value)
        }

    fun init(context: Context) {
        if (preference == null) preference =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun setIsLogin(key:String,query: Boolean) {
        preference?.edit()?.putBoolean(key,query)?.apply()

    }
    fun setArticleType(key:String,query: String) {
        preference?.edit()?.putString(key,query)?.apply()

    }
    fun setUserUID(key: String,query: String){
        preference?.edit()?.putString(key,query)?.apply()
    }
    fun getUserUID(key:String,defaultValue: String=""):String{
        return preference?.getString(key,defaultValue)!!
    }
    fun setUserDetails(key:String,query: UserDetails){
        val gson = Gson()
        val json: String = gson.toJson(query)
        preference?.edit()?.putString(key,json)?.apply()
    }
    fun getUserDetails(key: String, defaultValue: String = ""): UserDetails {
        val gson = Gson()
        val json: String = preference?.getString(key, defaultValue)!!
        return gson.fromJson(json, UserDetails::class.java)
    }

    fun setIsSetCategoryItems(key: String,query: Boolean){
        preference?.edit()?.putBoolean(key,query)?.apply()
    }
    fun getIsLogin(key:String, defaultValue:  Boolean= false): Boolean {
        return preference?.getBoolean(key,defaultValue)!!
    }
    fun getArticleType(key:String, defaultValue:  String= "Apple"): String {
        return preference?.getString(key,defaultValue)!!
    }

    fun getIsSetCategoryItems(key:String, defaultValue:  Boolean=false):Boolean{
        return preference?.getBoolean(key,defaultValue)!!
    }
    fun setAuthenticationVariable(key:String,query: String){
        preference?.edit()?.putString(key,query)?.apply()
    }
    fun getAuthenticationVariable(key:String,defaultValue: String=""):String{
        return preference?.getString(key,defaultValue)!!
    }

    fun setIsBreakingNewsApi(key: String,query: Boolean){
        preference?.edit()?.putBoolean(key,query)?.apply()
    }
    fun getIsBreakingNewsApi(key: String,defaultValue: Boolean=false):Boolean{
        return preference?.getBoolean(key,defaultValue)!!
    }
    fun setCodeToken(key: String,query: String) {
        preference?.edit()?.putString(key,query)?.apply()
    }
    fun getCodeToken(key:String,defaultValue: String=""):String{
        return preference?.getString(key,defaultValue)!!
    }


    @SuppressLint("CommitPrefEdits")
    override fun clearData() {
        preference?.edit()?.clear()
    }


}