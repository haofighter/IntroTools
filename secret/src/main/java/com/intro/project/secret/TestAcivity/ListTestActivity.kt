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
import com.intro.project.clock.service.AlarmService
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.intro.project.secret.MainActivity


class ListTestActivity : DrawarBaseActiivty(), View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(p0: View?) {
        var intent = Intent(this, AlarmService::class.java)
//        intent.putExtra(AppConstant().BACK_CALL, object : SpecialBackCall(com.intro.hao.mytools.R.id.set_a_clock) {
//
//            override fun deal(vararg objects: Any) {
//                Log.i("tag", "定时任务回调启动了")
//                ToastUtils().showMessage("定时任务回调启动了·")
//            }
//        })

        when (p0) {
            add_weiget -> {
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, (System.currentTimeMillis() + 10000) as Long)
                intent.putExtra(AppConstant().ALAERN_TAG, com.intro.hao.mytools.R.id.set_a_clock)
                startService(intent)
            }
            add_weiget1 -> {
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, (System.currentTimeMillis() + 20000) as Long)
                intent.putExtra(AppConstant().ALAERN_TAG, com.intro.hao.mytools.R.id.set_a_clock)
                startService(intent)
            }
            add_weiget2 -> {
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, (System.currentTimeMillis() + 30000) as Long)
                intent.putExtra(AppConstant().ALAERN_TAG, com.intro.hao.mytools.R.id.set_a_clock)
                startService(intent)
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


    }

    override fun LayoutID(): Int {
        return R.layout.activity_list_test
    }

}
