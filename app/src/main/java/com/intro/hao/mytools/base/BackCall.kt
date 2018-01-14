package com.intro.hao.mytools.base

/**
 * Created by haozhang on 2018/1/11.
 */
class BackCall {

    constructor(driverTopassenger: String) {}

    constructor() {}


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