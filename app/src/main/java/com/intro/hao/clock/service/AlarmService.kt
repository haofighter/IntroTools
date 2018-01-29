package com.intro.project.clock.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import com.intro.project.clock.receiver.AlarmReceiver
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.intro.project.clock.clockutils.SpecialBackCall
import com.intro.project.clock.contant.AppConstant


/**
 * 设置闹钟的一个服务
 * 开启服务时需要imntent传入参数 clockTime Long类型  这是闹钟启动的时间  intent的标示符 AppConstant().HORIZENSERVICE_TIM
 * repeatTime Long类型  闹钟重复的间隔时间 intent的标示符 AppConstant().REPEAT_TIME
 * backCall回调  SpecialBackCall对象   intent的标示符 AppConstant().BACK_CALL
 */
class AlarmService : Service() {
    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        var clockTime = intent.getLongExtra(AppConstant().HORIZENSERVICE_TIME, 0);
        var repeatTime = intent.getLongExtra(AppConstant().REPEAT_TIME, -1);
        val i = Intent(this, AlarmReceiver::class.java)
        if (intent.getSerializableExtra(AppConstant().BACK_CALL) != null)
            i.putExtra(AppConstant().BACK_CALL, intent.getSerializableExtra(AppConstant().BACK_CALL))
        val pi = PendingIntent.getBroadcast(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT)

        if (repeatTime == -1L) {
            Log.i("距离触发时间还有", "还剩" + ((clockTime + 1000 - System.currentTimeMillis()) / 1000) + "秒")
            manager.set(AlarmManager.RTC_WAKEUP, clockTime + 1000, pi)
        } else {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, clockTime, repeatTime, pi)
        }
        return super.onStartCommand(intent, flags, startId)
    }
}