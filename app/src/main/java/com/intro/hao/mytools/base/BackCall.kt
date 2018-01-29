package com.intro.hao.mytools.base

import java.io.Serializable

/**
 * Created by haozhang on 2018/1/11.
 */
abstract class BackCall : Serializable {

    constructor(driverTopassenger: String) {}

    constructor() {}

    abstract fun deal()

    abstract fun deal(tag: Any, vararg obj: Any)


}