package com.intro.hao.mytools.Utils.listener

/**
 * Created by haozhang on 2018/1/31.
 */
interface SearchFileLisener {
    fun updateProgress(vararg values: String?)
    fun finish(result: MutableList<Any?>?)
    fun onCancal()
    fun onStart()
}