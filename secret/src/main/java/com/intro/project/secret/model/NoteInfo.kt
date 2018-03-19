package com.intro.project.secret.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.Serializable


/**
 * 笔记信息表创建
 */

open class NoteInfo() : RealmObject(), Serializable {

    constructor(constent: String, time: Long, userID: Int, id: String, remindTime: Long) : this() {
        this.constent = constent
        this.time = time
        this.userID = userID
        this.id = id
        this.remindTime = remindTime
    }

    var constent: String = "" //内容
    var userID: Int = 0   //创建的用户id
    @PrimaryKey
    var id: String = ""   //唯一标识id
    var time: Long = 0    //创建的时间
    var remindTime: Long = 0    //提醒的时间

    override fun toString(): String {
        return constent + "  ----" + time + "  =====" + userID
    }

}