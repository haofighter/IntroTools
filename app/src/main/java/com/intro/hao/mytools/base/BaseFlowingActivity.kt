package com.intro.hao.mytools.base

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.DialogUtils
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.customview.FlowingDraw.ElasticDrawer
import kotlinx.android.synthetic.main.activity_base_base.*
import kotlinx.android.synthetic.main.flowing_content_layout.*


/**
 * 基础Activity
 */
abstract class BaseFlowingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.addActivty(this)
        super.setContentView(R.layout.activity_base_base)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.traslant)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setBackGroundDrowableResuoce(id: Int) {
        base.background = ContextCompat.getDrawable(this, id)
    }

    fun setBackGroundColorResuoce(id: Int) {
        base.setBackgroundColor(ContextCompat.getColor(this, id))
    }


    //用于修改宽度
    fun setSlidingWidth(size: Int) {
        flowingDrawer_base.setMenuSize(size)
    }

    fun setBaseBackGround(id: Int) {
        base.setBackgroundResource(id)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setBaseBackGround(drawable: Drawable) {
        base.background = drawable
    }


    override fun setContentView(layoutId: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutId, null))
    }

    override fun setContentView(layout: View) {
        base_content.removeAllViews()
        base_content.addView(layout)
        flowingDrawer_base.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE) //设置策划栏的触发模式
    }

    fun getContentView(): View {
        return flowingDrawer_base
    }


    fun getFlowingDrawerContentView(): View {
        return flowing_content_layout
    }

    fun setBaseFlowingDrawerContentView(layoutId: Int) {
        setFlowingDrawerContentView(LayoutInflater.from(this).inflate(layoutId, null))
    }

    fun setFlowingDrawerContentView(layout: View) {
        setSlidingWidth(SystemUtils().getViewSize(layout).measuredWidth)
        flowing_content_layout.removeAllViews()
        flowing_content_layout.addView(layout)
    }


    fun setFlowingDrawer(modle: Int) {
        flowingDrawer_base.setTouchMode(modle)
    }


    /**
     *打开侧滑栏
     *可以对侧滑的动画进行设置
     * 关联类  com.intro.hao.mytools.customview.FlowingDraw.FlowingMenuLayou
     * 方法名  setClipOffsetPixels()     drawLeftMenu()
     * 设置的参数   currentType
     */
    fun openSiding() {
        if (flowingDrawer_base != null) {
            flowingDrawer_base.toggleMenu()
        } else {
            Log.e("err", "侧滑栏是个空的")
            throw NullPointerException()
        }
    }

    fun closeSiding() {
        if (flowingDrawer_base != null) {
            flowingDrawer_base.closeMenu()
            flowingDrawer_base.setOnDrawerStateChangeListener(object : ElasticDrawer.OnDrawerStateChangeListener {
                override fun onDrawerStateChange(oldState: Int, newState: Int) {
                    Log.i("侧滑监听", "oldState==" + oldState + "newState==" + newState)
                }

                override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {
                    Log.i("侧滑监听", "openRatio==" + openRatio + "offsetPixels==" + offsetPixels)
                }
            })
        } else {
            Log.e("err", "侧滑栏是个空的")
            throw NullPointerException()
        }
    }


    fun showLoading() {
        DialogUtils.instance.LoadingDialog(this, false).show()
    }

    fun closeLoading() {
        DialogUtils.instance.LoadingDialog(this, false).dismiss()
    }


    /**
     * 跳转设置界面
     */
    protected fun getAppDetailSettingIntent() {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.action = Intent.ACTION_VIEW
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            intent.putExtra("com.android.settings.ApplicationPkgName", packageName)
        }
        startActivity(intent)
    }
}