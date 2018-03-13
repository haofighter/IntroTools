package com.intro.project.clock.clockutils

import android.app.PendingIntent

/**
 * Created by haozhang on 2018/1/15.
 */
class AlarmInfo constructor(tag: String, pendingIntent: PendingIntent, time: Long, reaptTime: Long) {
    var tag = tag
    var pendingIntent = pendingIntent
    var startTime = time
    var reaptTime = reaptTime
}