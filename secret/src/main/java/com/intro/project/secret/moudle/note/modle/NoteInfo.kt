package com.intro.project.secret.moudle.note.modle

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by haozhang on 2018/1/24.
 */
open class NoteInfo() : RealmObject() {
    constructor(id: String?, createTime: Long?, noteContent: String?) : this() {
        this.id = id
        this.createTime = createTime
        this.noteContent = noteContent
    }


    @PrimaryKey
    open var id: String? = ""
    open var createTime: Long? = null
    open var noteContent: String? = null
}
