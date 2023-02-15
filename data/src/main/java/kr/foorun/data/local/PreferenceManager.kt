package kr.foorun.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val preferences: SharedPreferences){

    fun setData(key:String, value:Any){
        when(value){
            is String -> setString(key, value)
            is Int -> setInt(key, value)
            is Boolean -> setBoolean(key, value)
            is HashSet<*> -> setStringList(key,value as HashSet<String>)
        }
    }

    private fun setStringList(key: String, value: Set<String>) = preferences.edit().putStringSet(key,value).apply()
    private fun setString(key:String, value:String) = preferences.edit().putString(key,value).apply()
    private fun setBoolean(key:String, value:Boolean) = preferences.edit().putBoolean(key,value).apply()
    private fun setInt(key:String, value:Int) = preferences.edit().putInt(key,value).apply()

    fun getStringList(key: String): MutableSet<String> = preferences.getStringSet(key,HashSet<String>()) ?: HashSet()
    fun getString(key:String): String = preferences.getString(key,"").toString() //return "" if it's null
    fun getBoolean(key:String): Boolean = preferences.getBoolean(key,true)
    fun getInt(key:String): Int = preferences.getInt(key,-1)
    fun clear() = preferences.edit().clear().apply()

    fun removeStringList(key: String, value: String) : MutableSet<String> {
        val set = HashSet(getStringList(key))
        set.remove(value)
        setStringList(key,set)
        return set
    }

    companion object{
        private const val PREFERENCES_NAME = "PRE"
        fun getPreferences(context: Context): SharedPreferences = context.getSharedPreferences(
            PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}