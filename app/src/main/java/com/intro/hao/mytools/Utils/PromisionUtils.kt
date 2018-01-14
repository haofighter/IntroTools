package com.intro.hao.mytools.Utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.intro.hao.mytools.base.App


/**
 * 设置
 */

class PromisionUtils(internal var context: Context) {

    fun ApplyPromise(activity: Activity, promisstionName: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(context, promisstionName) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(promisstionName), requestCode)
            return false
        } else {
            return true
        }
    }

    companion object instanse {
        fun get(): PromisionUtils {
            return Inner.instanse
        }

        private object Inner {
            val instanse = PromisionUtils(App())

        }
    }

}
