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
import android.os.Parcel
import com.intro.hao.mytools.constant.AppConstant
import com.intro.project.clock.clockutils.SpecialBackCall
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.intro.project.secret.MainActivity
import com.intro.project.secret.model.ClockInfo
import com.intro.project.secret.timechange.TimeChangeService
import com.vicpin.krealmextensions.save


class TestActivity : DrawarBaseActiivty(), View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(p0: View?) {

        when (p0) {
            add_weiget -> {
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, (System.currentTimeMillis() + 10000) as Long)
                intent.putExtra(AppConstant().ALAERN_TAG, com.intro.hao.mytools.R.id.set_a_clock)
                startService(intent)
            }
            add_weiget1 -> {
                Log.i("闹钟信息", "时间监听")

                val service = Intent(this, TimeChangeService::class.java)
                startService(service)
            }
            add_weiget2 -> {
                var clockInfo = ClockInfo()
                clockInfo.clockID = System.currentTimeMillis()
                clockInfo.clockStartTimeLong = System.currentTimeMillis() + 60000
                clockInfo.clockType = "1"
                clockInfo.reaptTime = 0
                clockInfo.save()

                var clockInfo1 = ClockInfo()
                clockInfo1.clockID = System.currentTimeMillis()
                clockInfo1.clockStartTimeLong = System.currentTimeMillis() + 120000
                clockInfo1.clockType = "1"
                clockInfo1.reaptTime = 0
                clockInfo1.save()
                Log.i("闹钟信息", "闹钟信息已存入数据库" + clockInfo.clockStartTimeLong + "-----" + clockInfo1.clockStartTimeLong)
            }
        }

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

        SoftKeyBoardListener.setListener(this, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                ToastUtils().showMessage("键盘显示 高度" + height + "   屏幕的高度" + SystemUtils().getScreenSize(this@TestActivity).heightPixels)
            }

            override fun keyBoardHide(height: Int) {
                ToastUtils().showMessage("键盘隐藏 高度" + height)
            }
        })

        add_weiget.setOnClickListener(this)
        add_weiget1.setOnClickListener(this)
        add_weiget2.setOnClickListener(this)
    }

    override fun LayoutID(): Int {
        return R.layout.activity_test
    }

}
