package com.intro.project.secret.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by hao on 2018/3/13.
 */
open class ClockInfo() : RealmObject() {
    @PrimaryKey
    var clockID: Long = 0  //唯一标识的id
    var clockType: String = "1"  //1为单次执行  2为工作日执行 3每天
    var clockStartTimeLong: Long = 0 //闹钟的开始时间
    var clockNoteId: String = ""  //关联的note的id
    var reaptTime: Long = 0  //关联的note的id

    constructor(clockID: Long, clockType: String, clockStartTimeLong: Long, clockNoteId: String, reaptTime: Long) : this() {
        this.clockID = clockID
        this.clockType = clockType
        this.clockStartTimeLong = clockStartTimeLong
        this.clockNoteId = clockNoteId
        this.reaptTime = reaptTime
    }
}