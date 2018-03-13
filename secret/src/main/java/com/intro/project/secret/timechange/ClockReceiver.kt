package com.intro.project.secret.timechange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.intro.project.secret.model.ClockInfo
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryLast

/**
 * Created by hao on 2018/3/13.
 */
class ClockReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        var clock = ClockInfo().queryLast { this.lessThan("clockStartTimeLong", System.currentTimeMillis() + 1) }
        Log.i("出发闹钟", "" + clock!!.clockStartTimeLong);
    }
}