package kr.foorun.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val preferences: SharedPreferences){

    fun setData(key:String, value:Any){
        when(value){
            is String -> setString(key, value)
            is Int -> setInt(key, value)
            is Boolean -> setBoolean(key, value)
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun setString(key:String, value:String) = preferences.edit().putString(key,value).apply()

    @SuppressLint("CommitPrefEdits")
    private fun setBoolean(key:String, value:Boolean) = preferences.edit().putBoolean(key,value).apply()

    @SuppressLint("CommitPrefEdits")
    private fun setInt(key:String, value:Int) = preferences.edit().putInt(key,value).apply()

    fun getString(key:String): String = preferences.getString(key,"").toString() //return "" if it's null
    fun getBoolean(key:String): Boolean = preferences.getBoolean(key,true)
    fun getInt(key:String): Int = preferences.getInt(key,-1)
    fun clear() = preferences.edit().clear().apply()

    companion object{
        private const val PREFERENCES_NAME = "PRE"
        fun getPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
            PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}