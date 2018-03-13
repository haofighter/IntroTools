package com.intro.project.secret.timechange

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.constant.AppConstant
import com.intro.project.clock.clockutils.AlarmInfo
import com.intro.project.clock.clockutils.ClockUtils
import com.intro.project.clock.clockutils.SpecialBackCall
import com.intro.project.secret.model.ClockInfo
import com.vicpin.krealmextensions.query

/**
 * Created by hao on 2018/3/13.
 */
class TimeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("tag", "查找到了一个闹钟 ")
        var intent = Intent(context, ClockService::class.java)
        intent.putExtra(AppConstant().BACK_CALL, object : SpecialBackCall(R.id.set_a_clock) {

            override fun deal(vararg objects: Any) {
                Log.i("tag", "定时任务回调启动了")
                ToastUtils().showMessage("定时任务回调启动了·")
            }
        })
        context!!.startService(intent)
    }
}