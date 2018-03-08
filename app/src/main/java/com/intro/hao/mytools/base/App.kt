package com.intro.hao.mytools.base

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.intro.hao.mytools.Utils.listener.OrientationListener

/**
 * Created by haozhang on 2018/1/3.
 */
open class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    //罗盘的角度
    //获取到罗盘的角度
    var currentX: Float = 0.toFloat()
        private set

    internal var activities: MutableList<Activity> = ArrayList()


    /**
     * 获取到罗盘信息
     */
    private fun getCompass() {
        val orientationListener = OrientationListener(this)
        orientationListener.setOnOrientationListener(object : OrientationListener.OnOrientationListener {
            override fun onOrientationChanged(x: Float) {
                currentX = x

            }
        })
        orientationListener.start()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f)
            getResources()
        //非默认值
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) {//非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()//设置默认
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }

    fun addActivty(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivty(activity: Activity) {
        activities.remove(activity)
    }

    fun finishActivty() {
        for (i in activities.indices) {
            activities[i].finish()
        }
    }

    var nowActivity: Activity? = null

}