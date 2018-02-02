package com.intro.hao.mytools.Utils.listener

import com.intro.hao.mytools.Utils.modle.MusicInfo

/**
 * Created by haozhang on 2018/1/31.
 * 用于使用AsyncTask针对文件夹进行遍历音乐
 * 回调于AsyncTask的各个进度和状态
 */
interface SearchMusicLisener {
    fun updateProgress(vararg values: String?)
    fun finish(result: MutableList<MusicInfo>?)
    fun onCancal()
    fun onStart()
}