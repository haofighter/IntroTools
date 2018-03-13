package com.intro.project.secret.timechange

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.intro.project.clock.clockutils.AlarmInfo
import com.intro.project.clock.clockutils.ClockUtils
import com.intro.project.secret.base.BaseApp
import com.intro.project.secret.model.ClockInfo
import com.vicpin.krealmextensions.query

/**
 * 闹钟的设置服务
 */
class ClockService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        intent!!.setClass(this, ClockReceiver::class.java)

//        var clockInfo = ClockInfo().queryAll()
        var clockInfo = ClockInfo().query { this.greaterThan("clockStartTimeLong", System.currentTimeMillis()) }
        if (clockInfo != null) {
            for (item in clockInfo) {
                ClockUtils.get().addAlarmClock(AlarmInfo("" + item.clockID, PendingIntent.getBroadcast(BaseApp.instance, 1,
                        intent!!.setClass(BaseApp.instance, ClockReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT), item.clockStartTimeLong, item.reaptTime))
            }
        }

        for (item in ClockUtils.get().pendingIntents) {
            if (item!!.startTime == 0L) {
                Log.i("距离触发时间还有", "还剩" + ((item!!.startTime - System.currentTimeMillis()) / 1000) + "秒")
                manager.set(AlarmManager.RTC_WAKEUP, item!!.startTime, item.pendingIntent)
            } else {
                manager.setRepeating(AlarmManager.RTC_WAKEUP, item!!.startTime, item!!.reaptTime, item.pendingIntent)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}