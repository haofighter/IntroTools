package com.intro.hao.mytools.Utils.modle

import io.realm.RealmObject

/**
 * Created by haozhang on 2018/2/1.
 */
open class VadioFileInfo {
    open var url: String? = null
    open var year: String? = null       //出版日期
    open var album: String? = null   //专辑名
    open var artist: String? = null //歌手名
    open var duration: Int? = null  //时常
    open var mimetype: String? = null   //音乐类型
    open var bitrate: String? = null   //音乐比特率
    open var tilte: String? = null  //名称
}