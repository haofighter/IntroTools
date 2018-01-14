package com.intro.hao.mytools.ResponseBean

/**
 * Created by haozhang on 2017/12/29.
 */
class BaseResponce {

    var code: Int = 0
    var message: String = ""
    override fun toString(): String {
        return "BaseResponce(code=$code)"
    }
}