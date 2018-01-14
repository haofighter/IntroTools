package com.intro.hao.mytools.home.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import com.intro.hao.mytools.home.receiver.AlarmReceiver
import android.content.Intent
import android.os.SystemClock
import android.content.Context.ALARM_SERVICE
import android.os.IBinder
import android.text.format.DateUtils
import android.util.Log
import com.intro.hao.mytools.Utils.DataUtils
import java.util.*


/**
 * Created by haozhang on 2018/1/4.
 */
class HorizonService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        var clockTime = intent.getLongExtra("ClockTime", 0);
        Log.i("clockTime", DataUtils().FormatDate(clockTime))
        val i = Intent(this, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, 0, i, 0)
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, clockTime, 24 * 60 * 60 * 1000, pi)
        return super.onStartCommand(intent, flags, startId)
    }
}