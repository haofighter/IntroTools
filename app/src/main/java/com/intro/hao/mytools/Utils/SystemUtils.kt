package com.intro.hao.mytools.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.AsyncTask
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.listener.SearchFileLisener
import com.intro.hao.mytools.base.App
import com.intro.hao.mytools.base.BackCall
import java.io.File


/**
 * 集成了跟系统相关的工具
 * 1.像素和DP之间的转换
 * 2.获取状态栏高度
 * 3.遍历文件夹
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


    fun getViewSize(v: View): View {
        val w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED)
        v.measure(w, h)
        val height = v.getMeasuredHeight()
        val width = v.getMeasuredWidth()
        return v
    }


    /**
     * path 搜索文件的最初目录
     * filestag 搜索的文件的表示 可以是特定的表示  也可以是后缀
     */
    tailrec fun traverseFile(path: String, filesTag: MutableList<String>, backCall: BackCall): MutableList<String> {
        var searchFiles = mutableListOf<String>()
        val dir = File(path)//文件夹dir
        backCall.deal("file", path)
        val files = dir.listFiles() ?: return mutableListOf<String>()//文件夹下的所有文件或文件夹
        for (i in files) {
            if (i.isDirectory()) {
                searchFiles.addAll(traverseFile(i.getAbsolutePath(), filesTag, backCall))//递归文件夹！！！
            } else {
                for (item in filesTag) {
                    if (i.path.endsWith(item)) {
                        searchFiles.add(i.absolutePath)
                    }
                }
            }
        }
        return searchFiles
    }

    var searchFileAsyncTask: SearchFileAsyncTask? = null
    fun searchFlie(tags: MutableList<String>, searchFileLisener: SearchFileLisener) {
        if (ActivityCompat.checkSelfPermission(App.instance, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            searchFileAsyncTask = SearchFileAsyncTask(searchFileLisener)
            searchFileAsyncTask!!.execute(tags)
        } else {
            DialogUtils.instance.showInfoDialog(App.instance, "提示", "您还未获取到相关的操作权限是,无法使用此功能/n是否进行申请", "申请", "取消", object : BackCall() {
                override fun deal() {
                }

                override fun deal(tag: Any, vararg obj: Any) {
                    when (tag) {
                        R.id.confirm -> {
                            if (App.instance.nowActivity != null) {
                                ActivityCompat.requestPermissions(App.instance.nowActivity!!, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
                            } else {
                                throw NullPointerException("未获取到 当前的activity ,  请尝试调用此方法之前调用 App.instance.nowActivity = this(Activity)")

                            }
                        }

                    }
                }
            })
        }
    }

    fun cancalSearchFileAsyncTask() {
        if (searchFileAsyncTask != null && !searchFileAsyncTask!!.isCancelled) {
            searchFileAsyncTask!!.cancel(true)
            searchFileAsyncTask = null
        }
    }


    class SearchFileAsyncTask constructor(searchFileLisener: SearchFileLisener) : AsyncTask<MutableList<String>, String, MutableList<String>>() {
        var listener = searchFileLisener
        override fun doInBackground(vararg p0: MutableList<String>): MutableList<String>? {
            if (isCancelled()) {//执行中断方法 cancalSearchFileAsyncTask后只是给task设置一个状态  并没有对task进行处理 需要在此位置进行处理
                return mutableListOf<String>()
            }

            return SystemUtils().traverseFile(Environment.getExternalStorageDirectory().path, p0[0], object : BackCall() {
                override fun deal() {

                }

                override fun deal(tag: Any, vararg obj: Any) {
                    when (tag) {
                        "file" -> publishProgress(obj[0] as String)
                    }
                }
            })
        }

        override fun onPreExecute() {
            super.onPreExecute()
            listener.onStart()
        }


        override fun onCancelled() {
            super.onCancelled()
            listener.onCancal()
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            listener.updateProgress(*values)
        }

        override fun onPostExecute(result: MutableList<String>?) {
            super.onPostExecute(result)
            Log.i("进度", "总共搜索" + result!!.size)
            listener.finish(result)
        }
    }
}
