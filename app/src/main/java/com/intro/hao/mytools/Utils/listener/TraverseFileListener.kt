package com.intro.hao.mytools.Utils.listener

/**
 * Created by haozhang on 2018/2/1.
 * 添加于文件遍历时的监听
 * 用于监听正在遍历的文件和文件夹
 * 可能存在线程安全问题
 */
interface TraverseFileListener {
    fun traverseFileItem(file: String): Any?
    fun traverseDirectoryItem(directroy: String)
}