package com.intro.hao.mytools.home.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.home.service.HorizonService

/**
 * Created by haozhang on 2018/1/4.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ToastUtils().showMessage("闹铃响了, 可以做点事情了~~")
        Log.i("tag", "触发了闹铃");
        val i = Intent(context, HorizonService::class.java)
        context!!.startService(i)
    }
}