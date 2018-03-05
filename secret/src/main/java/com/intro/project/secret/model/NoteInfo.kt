package com.intro.project.secret.model

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.RealmClass


/**
 * Created by hao on 2018/3/5.
 */

open class NoteInfo() : RealmObject() {

    constructor(constent: String, time: Long) : this() {
        this.constent = constent
        this.time = time
    }

    var constent: String = ""
    var time: Long = 0

}