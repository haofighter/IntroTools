package com.intro.project.secret.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


/**
 * Created by hao on 2018/3/5.
 */

open class NoteInfo() : RealmObject() {

    constructor(constent: String, time: Long, userID: Int) : this() {
        this.constent = constent
        this.time = time
        this.userID = userID
    }

    var constent: String = ""
    var userID: Int = 0
    @PrimaryKey
    var time: Long = 0

    override fun toString(): String {
        return constent + "  ----" + time + "  =====" + userID
    }

}