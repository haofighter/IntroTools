package com.intro.project.secret.TestAcivity

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.intro.hao.mytools.Utils.*
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.project.secret.R
import com.intro.project.secret.base.DrawarBaseActiivty
import com.intro.project.secret.moudle.view.SideLayout
import kotlinx.android.synthetic.main.activity_test.*
import android.content.Intent
import android.app.Activity
import android.appwidget.AppWidgetProviderInfo
import android.widget.Toast
import android.content.ComponentName
import android.os.Bundle
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.intro.project.secret.MainActivity


class TestActivity : DrawarBaseActiivty(), View.OnClickListener {


    private val MY_REQUEST_APPWIDGET = 1
    private val MY_CREATE_APPWIDGET = 2
    var appWidgetManager: AppWidgetManager? = null
    var mAppWidgetHost: AppWidgetHost? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(p0: View?) {
//        val APPWIDGET_HOST_ID = 1024
//        val appWidgetHost = AppWidgetHost(applicationContext, APPWIDGET_HOST_ID)
//        val appWidgetId = appWidgetHost.allocateAppWidgetId()
//
//        var pickIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
//        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        // add the search widget
//        val customInfo = ArrayList<AppWidgetProviderInfo>()
//        var info = AppWidgetProviderInfo();
//        info.provider = ComponentName(getPackageName(), ".moudle.note.EditNoteActivity");
//        info.label = "测是";
//        info.icon = R.mipmap.icon1;
//        customInfo.add(info);
//        pickIntent.putParcelableArrayListExtra(
//                AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
//        var customExtras =  ArrayList<Bundle>();
//        var b =  Bundle();

//        b.putString(EXTRA_CUSTOM_WIDGET, SEARCH_WIDGET);
//        customExtras.add(b);
//        pickIntent.putParcelableArrayListExtra(
//                AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
//        // start the pick activity
//        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);

        val shortcutIntent = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.abc_action_bar_home_description))
        shortcutIntent.putExtra("duplicate", false)
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClass(applicationContext, MainActivity::class.java)

        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent)
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this@TestActivity,
                        R.mipmap.ic_launcher))
        sendBroadcast(shortcutIntent)

    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun initView() {
        setRightLayoutBackGround(ContextCompat.getDrawable(this, R.mipmap.main1))
        setDrawerContentView(SideLayout(this, drawer))
        navigation.setTitle("测试一下原生的drawaer")
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.LEFT_VIEW -> openLeft()
                }
                return true
            }

        })


//        SystemUtils().searchFlie(mutableListOf<String>(".mp3", ".wam", ".apc", ".aac"), object : SearchFileLisener {
//            override fun updateProgress(vararg values: String?) {
//                Log.i("更新", values[0])
//            }
//
//            override fun finish(result: MutableList<Any?>?) {
//                Log.i("进度", "总共搜索" + result!!.size)
//            }
//
//            override fun onCancal() {
//                Log.i("中断", "onCancal")
//            }
//
//            override fun onStart() {
//                Log.i("开始", "开始搜索")
//            }
//        }, object : TraverseFileListener {
//            override fun traverseFileItem(file: String): Any? {
//                return MusicUtils().getMusicInfoFromPath(file)
//            }
//
//            override fun traverseDirectoryItem(directroy: String) {
//            }
//        })


        SoftKeyBoardListener.setListener(this, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                ToastUtils().showMessage("键盘显示 高度" + height + "   屏幕的高度" + SystemUtils().getScreenSize(this@TestActivity).heightPixels)
            }

            override fun keyBoardHide(height: Int) {
                ToastUtils().showMessage("键盘隐藏 高度" + height)
            }
        })


        add_weiget.setOnClickListener(this)
    }

    override fun LayoutID(): Int {
        return R.layout.activity_test
    }

}
