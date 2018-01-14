package com.intro.hao.mytools.base

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.view.NavigationBar
import kotlinx.android.synthetic.main.toolbar_base_activity.*
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import com.intro.hao.mytools.Utils.StatusBarUtil


/**
 * Created by haozhang on 2018/1/9.
 */
open abstract class ToolBarBaseActivity : BaseActivity() {
    abstract override fun setContentView(): Int

    abstract override fun setFlowingDrawerContentView(): Int


    override fun BaseContentLayoutId(): Int {
        return R.layout.toolbar_base_activity
    }

    lateinit var navigation: NavigationBar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 用于判断状态栏颜色 便于识别
         */
        var navBackGround = navigationbar.background
        if (navBackGround is ColorDrawable) {
            Log.i("背景颜色", "获取到的图片背景" + navBackGround.color);
            if (navBackGround.color == -1)
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray_44)
        } else if (navBackGround is BitmapDrawable) {
            var bgBitmap = navBackGround.bitmap  //获取到了背景图片
            Log.i("背景颜色", "获取到的图片背景");
        }
    }

    /**
     * 自行填充界面
     */
    override fun setContentView(layoutId: Int) {
        var view = LayoutInflater.from(this).inflate(layoutId, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(view)
    }


    override fun setContentView(layout: View) {
        super.setContentView(BaseContentLayoutId())
        main_content.addView(layout)
        navigationbar.setPadding(0, SystemUtils().getDecorViewHightByReflect(this), 0, 0)
        navigation = navigationbar
    }
}