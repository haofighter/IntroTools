package com.intro.hao.mytools.base

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.DialogUtils
import com.intro.hao.mytools.Utils.KeyboardChangeListener
import com.intro.hao.mytools.Utils.SystemUtils
import kotlinx.android.synthetic.main.activity_drawer_base.*


/**
 * 基础Activity
 */
abstract class BaseDrawerActivity : AppCompatActivity() {
    var leftView: View? = null
    var rightView: View? = null
    final val DERWAR_LEFT = 1
    final val DERWAR_RIGHT = 1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.addActivty(this)
        super.setContentView(R.layout.activity_drawer_base)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = ContextCompat.getColor(this, R.color.traslant)

    }

    fun setKeyboardChangeListener(keyBoardListener: KeyboardChangeListener.KeyBoardListener) {
        KeyboardChangeListener(this).setKeyBoardListener(keyBoardListener)
    }


    override fun setContentView(layoutId: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutId, null))
    }

    override fun setContentView(layout: View) {
        base_content.removeAllViews()
        base_content.addView(layout)
    }

    fun getContentView(): View? {
        return base_content
    }


    fun setFlowingDrawerContentView(layout: View, model: Int) {
        setSlidingWidth(SystemUtils().getViewSize(layout).measuredWidth, model)
        if (model == DERWAR_LEFT) {
            flowing_content_left_layout.removeAllViews()
            flowing_content_left_layout.addView(layout)
        } else if (model == DERWAR_RIGHT) {
            flowing_content_right_layout.removeAllViews()
            flowing_content_right_layout.addView(layout)
        }
    }

    fun setSlidingWidth(measuredWidth: Int, model: Int) {
        if (model == DERWAR_LEFT) {
            flowing_content_left_layout.layoutParams = DrawerLayout.LayoutParams(SystemUtils().getViewSize(flowing_content_left_layout).measuredHeight, measuredWidth)
        } else if (model == DERWAR_RIGHT) {
            flowing_content_right_layout.layoutParams = DrawerLayout.LayoutParams(SystemUtils().getViewSize(flowing_content_right_layout).measuredHeight, measuredWidth)
        }
    }

    fun openLeft() {
        if (drawerLayout.isDrawerOpen(flowing_content_left_layout)) {
            drawerLayout.closeDrawer(flowing_content_left_layout);
        } else {
            drawerLayout.openDrawer(flowing_content_left_layout);
        }
    }

    fun openRight() {
        if (drawerLayout.isDrawerOpen(flowing_content_right_layout)) {
            drawerLayout.closeDrawer(flowing_content_right_layout);
        } else {
            drawerLayout.openDrawer(flowing_content_right_layout);
        }
    }

    fun closeAllSiding() {
        closeLeft()
        closeRight()
    }

    fun closeLeft() {
        if (drawerLayout.isDrawerOpen(flowing_content_left_layout)) {
            drawerLayout.closeDrawer(flowing_content_left_layout);
        } else {
            drawerLayout.openDrawer(flowing_content_left_layout);
        }
    }

    fun closeRight() {
        if (drawerLayout.isDrawerOpen(flowing_content_right_layout)) {
            drawerLayout.closeDrawer(flowing_content_right_layout);
        } else {
            drawerLayout.openDrawer(flowing_content_right_layout);
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