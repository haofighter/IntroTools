package com.intro.project.secret.timechange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by hao on 2018/3/13.
 */
class TimeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("闹钟", "时间改变了")
        var intent = Intent(context, ClockService::class.java)
        context!!.startService(intent)
    }
}