package com.intro.hao.mytools.Utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by haozhang on 2018/1/3.
 */
class DataUtils {


    /**
     * 将long类型的数据和 date类型的数据转成 yyyy-MM-dd HH:mm:ss
     */
    fun FormatDate(time: Any?): String? {
        var formatDateString: String = ""
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            if (time is Long) {
                val date = Date(time!!.toString().toLong())
                formatDateString = sdf.format(date)
            } else if (time is Date) {
                formatDateString = sdf.format(time)
            }
        } catch (e: Exception) {
            Log.e("时间转换错误", "位置 Utils.FormatDate")
            return ""
        }
        return formatDateString
    }

    fun FormatDateMonthDayChineses(time: String): Long {
        try {
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            val date = sdf.parse(time)
            return date.time
        } catch (e: Exception) {
            Log.e("时间转换错误", "位置 Utils.FormatDate")
            return 0
        }

    }


    /**
     * 使用日历来获取时间
     */
    fun getMonthDay(date: Any): String {
        val cal = Calendar.getInstance()
        if (date is Long) {
            cal.timeInMillis = date
        } else if (date is Date) {
            cal.time = date
        }
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return year.toString() + "年" + month + "月" + day + "日"
    }

}