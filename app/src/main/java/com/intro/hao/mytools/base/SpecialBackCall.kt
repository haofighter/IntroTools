package com.intro.hao.mytools.base

import java.io.Serializable

/**
 * 用于实现某些需要传入对象后进行操作而创建的一个特殊回调
 */
abstract class SpecialBackCall : Serializable {
    abstract fun deal(vararg objects: Any)
}