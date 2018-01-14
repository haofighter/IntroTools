package com.intro.hao.mytools.Utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * 集成了跟系统相关的工具
 * 1.像素和DP之间的转换
 * 2.获取状态栏高度
 */
class SystemUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     *
     * 应用区的顶端位置即状态栏的高度
     * *注意*该方法不能在初始化的时候用
     * */
    fun getDecorViewHight(activity: Activity): Int {
        val rectangle = Rect()
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    /**
     *
     * 应用区的顶端位置即状态栏的高度
     * *注意*该方法不能在初始化的时候用
     * */
    fun getDecorViewHightByReflect(activity: Activity): Int {
        var statusBarHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val obj = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(obj).toString())
            statusBarHeight = activity.getResources().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight;
    }

    /**
     * 状态栏高度 = 屏幕高度 - 应用区高度
     * *注意*该方法不能在初始化的时候用
     * */
    fun getDecorViewHightByAllHight(activity: Activity): Int {
        //屏幕的高度
        val dm = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm)
        //应用区域
        val outRect1 = Rect()
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1)
        return dm.heightPixels - outRect1.height()  //状态栏高度=屏幕高度-应用区域高度
    }


    fun getScreenSize(activity: Activity): DisplayMetrics {
        val manager = activity.getWindowManager()
        val outMetrics = DisplayMetrics()
        manager.getDefaultDisplay().getMetrics(outMetrics)
        return outMetrics;
    }
}