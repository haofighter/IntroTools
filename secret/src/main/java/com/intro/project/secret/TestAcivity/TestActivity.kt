package com.intro.project.secret.TestAcivity

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.intro.hao.mytools.Utils.*
import com.intro.hao.mytools.Utils.listener.SearchFileLisener
import com.intro.hao.mytools.Utils.listener.TraverseFileListener
import com.intro.hao.mytools.base.BaseToolBarActivity
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.project.secret.R
import com.intro.project.secret.moudle.view.SideLayout


class TestActivity : BaseToolBarActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun initView() {
        setRightLayoutBackGround(ContextCompat.getDrawable(this, R.mipmap.main1))
        setDrawerContentView(SideLayout(this))
        navigation.setTitle("测试一下原生的drawaer")
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.LEFT_VIEW -> openLeft()
                }
                return true
            }

        })


        SystemUtils().searchFlie(mutableListOf<String>(".mp3", ".wam", ".apc", ".aac"), object : SearchFileLisener {
            override fun updateProgress(vararg values: String?) {
                Log.i("更新", values[0])
            }

            override fun finish(result: MutableList<Any?>?) {
                Log.i("进度", "总共搜索" + result!!.size)
            }

            override fun onCancal() {
                Log.i("中断", "onCancal")
            }

            override fun onStart() {
                Log.i("开始", "开始搜索")
            }
        }, object : TraverseFileListener {
            override fun traverseFileItem(file: String): Any? {
                return MusicUtils().getMusicInfoFromPath(file)
            }

            override fun traverseDirectoryItem(directroy: String) {
            }
        })


        SoftKeyBoardListener.setListener(this, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                ToastUtils().showMessage("键盘显示 高度" + height + "   屏幕的高度" + SystemUtils().getScreenSize(this@TestActivity).heightPixels)
            }

            override fun keyBoardHide(height: Int) {
                ToastUtils().showMessage("键盘隐藏 高度" + height)
            }
        })
    }

    override fun LayoutID(): Int {
        return R.layout.activity_test
    }


}
