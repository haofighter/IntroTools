package com.intro.hao.mytools.base

import java.io.Serializable

/**
 * Created by haozhang on 2018/1/11.
 */
abstract class BackCall : Serializable {

    constructor(driverTopassenger: String) {}

    constructor() {}

    abstract fun deal()

    fun deal(tag: String) {

    }

    fun deal(tag: Int) {

    }

    fun deal(tag: String, vararg obj: Any) {

    }

    fun deal(tag: Int, vararg obj: Any) {

    }

    fun dealForResult(tag: String, vararg obj: Any): Boolean {
        return true
    }

    fun dealForResult(tag: Int, vararg obj: Any): Boolean {
        return true
    }

}