package com.intro.hao.mytools.Utils.listener

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Created by haozhang on 2018/1/3.
 */
class OrientationListener : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private val mContext: Context
    private var lastX: Float = 0.toFloat()
    private var mOnOrientationListener: OnOrientationListener? = null

    constructor(mContext: Context) {
        this.mContext = mContext
    }


    fun start() {
        mSensorManager = mContext
                .getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorManager != null) {
            //获得方向传感器
            mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        }
        //判断是否有方向传感器
        if (mSensor != null) {
            //注册监听器
            mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)

        }


    }

    fun stop() {
        mSensorManager!!.unregisterListener(this)

    }

    //方向改变
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
            val x = event.values[SensorManager.DATA_X]
            if (Math.abs(x - lastX) > 1.0) {
                if (mOnOrientationListener != null) {
                    mOnOrientationListener!!.onOrientationChanged(x)
                }
            }
            lastX = x
        }
    }

    fun setOnOrientationListener(listener: OnOrientationListener) {
        mOnOrientationListener = listener
    }

    interface OnOrientationListener {
        fun onOrientationChanged(x: Float)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }
}