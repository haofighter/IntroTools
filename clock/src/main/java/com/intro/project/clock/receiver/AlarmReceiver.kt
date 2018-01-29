package com.intro.project.clock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.intro.project.clock.clockutils.SpecialBackCall
import com.intro.project.clock.contant.AppConstant

/**
 * 定时任务的广播
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.getSerializableExtra(AppConstant().BACK_CALL) != null) {
            var backCall = intent.getSerializableExtra("backcall") as SpecialBackCall
            backCall.deal("")
        }
    }
}