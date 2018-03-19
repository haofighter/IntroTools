package com.intro.project.secret.timechange

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log

/**
 * Created by hao on 2018/3/13.
 */
class TimeChangeService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        registerReceiver(TimeChangeReceiver(), filter)
        return super.onStartCommand(intent, flags, startId)
    }
}