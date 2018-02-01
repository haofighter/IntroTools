package com.intro.project.secret.TestAcivity

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.Utils.listener.SearchFileLisener
import com.intro.hao.mytools.base.BaseToolBarActivity
import com.intro.hao.mytools.view.NavigationBar
import com.intro.hao.mytools.view.NavigationTag
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

            override fun finish(result: MutableList<String>?) {
                Log.i("进度", "总共搜索" + result!!.size)
            }

            override fun onCancal() {
                Log.i("中断", "onCancal")
            }

            override fun onStart() {
                Log.i("开始", "开始搜索")
            }
        })
    }

    override fun LayoutID(): Int {
        return R.layout.activity_test
    }


}
