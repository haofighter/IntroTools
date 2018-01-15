package com.intro.hao.mytools.base

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.DialogUtils
import com.intro.hao.mytools.Utils.StatusBarUtil
import com.intro.hao.mytools.customview.FlowingDraw.ElasticDrawer
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.flowing_content_layout.*

/**
 * 基础Activity
 */
abstract class BaseActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = ContextCompat.getColor(this, R.color.traslant)
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
}