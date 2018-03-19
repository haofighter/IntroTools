package com.intro.project.secret.timechange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.intro.project.clock.clockutils.ClockUtils
import com.intro.project.secret.model.ClockInfo
import com.vicpin.krealmextensions.query
import android.app.Activity
import android.app.AlarmManager
import android.util.Log
import com.vicpin.krealmextensions.delete


/**
 * Created by hao on 2018/3/13.
 */
class ClockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val am = context!!.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

        if (ClockUtils.get().pendingIntents.size != 0) {
            am.cancel(ClockUtils.get().pendingIntents[0].pendingIntent)
            ClockUtils.get().pendingIntents.clear()
//            var clockInfo = ClockInfo().query { equalTo("clockType", "1").and().lessThan("clockStartTimeLong", System.currentTimeMillis()) }.sortedByDescending { }
//            Log.i("tag", "闹钟调用一次" + clockInfo[0].clockStartTimeLong)
            var clockInfos = ClockInfo().query { equalTo("clockType", "1").and().lessThan("clockStartTimeLong", System.currentTimeMillis()) }.sortedByDescending { "clockStartTimeLong" }

            for (item in clockInfos) {
                Log.i("触发闹钟后", "关联的id" + item.clockNoteId)
            }
        }
    }

}