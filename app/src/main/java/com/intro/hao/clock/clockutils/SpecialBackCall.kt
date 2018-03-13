package com.intro.project.clock.clockutils

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * 用于实现某些需要传入对象后进行操作而创建的一个特殊回调
 */
abstract class SpecialBackCall(set_a_clock: Int) : Serializable {
    var backtag = set_a_clock
    abstract fun deal(vararg objects: Any)

}