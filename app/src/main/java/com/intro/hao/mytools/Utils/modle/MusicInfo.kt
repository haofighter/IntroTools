package com.intro.hao.mytools.Utils.modle

import android.graphics.Bitmap

/**
 * Created by haozhang on 2018/2/1.
 */
class MusicInfo : VadioFileInfo() {

    open var id: Int? = null //歌曲id
    open var size: Long? = null
    open var bitmap: Bitmap? = null
    open var display_name: String? = null //歌曲文件名

}