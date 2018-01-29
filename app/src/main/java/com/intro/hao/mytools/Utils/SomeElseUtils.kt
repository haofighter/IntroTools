package com.intro.hao.mytools.Utils

/**
 * Created by haozhang on 2018/1/25.
 */
class SomeElseUtils {

     fun getRadomChar(num: Int): String {
        var i = 0
        var radomChar = ""
        while (i < num) {
            i++
            val chars = "abcdefghijklmnopqrstuvwxyz"
            radomChar += chars[(Math.random() * 26).toInt()]
        }
        return radomChar
    }
}