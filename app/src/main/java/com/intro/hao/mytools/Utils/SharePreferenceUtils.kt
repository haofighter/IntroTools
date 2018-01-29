package com.intro.hao.mytools.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.intro.hao.mytools.base.App
import android.R.id.edit
import android.util.Log


/**
 * Created by haozhang on 2018/1/15.
 */
class SharePreferenceUtils {

    private constructor() {
        if (sharedPreferences == null)
            sharedPreferences = App.instance.getSharedPreferences(
                    "share_file", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    companion object {
        fun get(): SharePreferenceUtils {
            return Inner.sharePreferenceUtils
        }
    }

    private object Inner {
        val sharePreferenceUtils = SharePreferenceUtils()
    }


    internal var sharedPreferences: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null


    var allTags = mutableMapOf<String, String>()

    fun setDate(tag: String, date: Any) {
        var type: String = ""
        if (date is String) {
            type = "String"
            editor!!.putString(tag, date)
        } else if (date is Int) {
            type = "Int"
            editor!!.putInt(tag, date)
        } else if (date is Boolean) {
            type = "Boolean"
            editor!!.putBoolean(tag, date)
        } else if (date is Float) {
            type = "Float"
            editor!!.putFloat(tag, date)
        } else if (date is Long) {
            type = "Long"
            editor!!.putLong(tag, date)
        } else if (date is Double) {
            type = "Float"
            editor!!.putFloat(tag, date as Float)
        }

        if (!allTags.containsKey(tag) && !type.equals("")) {
            allTags.put(tag, type)
        }
        Log.i("share的添加", "" + allTags.size + "tag==" + tag + "=====date=====" + date)
        editor!!.commit()
    }

    fun setDate(tag: String, date: Set<String>) {
        editor!!.putStringSet(tag, date)
    }

    fun getDate(str: String): Any? {
        for ((k, v) in allTags) {
            if (k.equals(str)) {
                if (v.equals("String")) {
                    return sharedPreferences!!.getString(k, "");
                } else if (v.equals("Int")) {
                    return sharedPreferences!!.getInt(k, -1);
                } else if (v.equals("Boolean")) {
                    return sharedPreferences!!.getBoolean(k, false);
                } else if (v.equals("Float")) {
                    return sharedPreferences!!.getFloat(k, -1F);
                } else if (v.equals("Long")) {
                    return sharedPreferences!!.getLong(k, -1L);
                } else if (v.equals("Long")) {
                    return sharedPreferences!!.getLong(k, -1L);
                }
            }
        }
        return null
    }

    fun clear() {
        for ((k, v) in allTags) {
            println("$k -> $v")
            Log.i("sharemap1", "=======" + k + "=====" + v)
            Log.i("sharemap", "" + getDate(k) + "=======" + k + "=====" + v)
            editor!!.remove(k)
        }
        allTags.clear()
    }

}