package com.intro.hao.mytools.base

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.BitmapUtils
import com.intro.hao.mytools.Utils.DialogUtils
import com.intro.hao.mytools.Utils.KeyboardChangeListener
import kotlinx.android.synthetic.main.activity_side_base.*


/**
 * 基础Activity  使用原生侧滑栏
 * 用于需要对界面软键盘进行监听的需求
 */
abstract class BaseDrawerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.nowActivity = this
        App.instance.addActivty(this)
        super.setContentView(R.layout.activity_side_base)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.traslant)
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)  //设置禁止拖拽
    }

    fun setBaseBackGround(id: Int) {
        drawerLayout.setBackgroundResource(id)
    }

    fun setBaseBackGround(drawable: Drawable) {
        drawerLayout.background = drawable
    }

    fun setDrawaerCanDrag() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)  //设置可以拖拽
    }


    //设置软件盘的监听
    fun setKeyboardChangeListener(keyBoardListener: KeyboardChangeListener.KeyBoardListener) {
        KeyboardChangeListener(this).setKeyBoardListener(keyBoardListener)
    }

    fun setRightLayoutParamt(layoutParams: RelativeLayout.LayoutParams) {
        siding_content_left_layout.layoutParams = layoutParams
    }

    fun setRightLayoutWidth(width: Int) {
        siding_content_left_layout.layoutParams = RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.MATCH_PARENT)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setRightLayoutBackGround(drawable: Drawable) {
        siding_content_left_layout.background = BitmapUtils().blurDrawable(drawable, 20f)
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


    fun setDrawerContentView(layout: View) {
//        setDrawerContentViewWidth(SystemUtils().getViewSize(layout).measuredWidth)
        siding_content_left_layout.removeAllViews()
        siding_content_left_layout.addView(layout)
    }

    fun getDrawerContentView(): View {
        return siding_content_left_layout
    }


    fun setDrawerContentViewWidth(measuredWidth: Int) {
        Log.i("侧滑测长度", "     " + measuredWidth);
        siding_content_left_layout.layoutParams = DrawerLayout.LayoutParams(measuredWidth, DrawerLayout.LayoutParams.MATCH_PARENT)
    }

    fun openLeft() {
        if (!drawerLayout.isDrawerOpen(siding_content_left_layout)) {
            drawerLayout.openDrawer(siding_content_left_layout);
        }
    }


    fun closeLeft() {
        drawerLayout.closeDrawer(siding_content_left_layout);
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