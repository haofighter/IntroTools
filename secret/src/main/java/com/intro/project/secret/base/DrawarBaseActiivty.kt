package com.intro.project.secret.base

import android.content.Intent
import android.support.v4.widget.DrawerLayout
import android.view.View
import com.intro.hao.mytools.base.App
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.base.BaseToolBarActivity
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.project.secret.R
import com.intro.project.secret.TestAcivity.TestActivity
import com.intro.project.secret.moudle.music.MusicHomeFActivity
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.intro.project.secret.moudle.schedule.ScheduleActivity
import com.intro.project.secret.moudle.view.SideLayout


/**
 * Created by haozhang on 2018/1/23.
 */
abstract class DrawarBaseActiivty : BaseToolBarActivity() {
    override fun initView() {
        setDrawerContentView(SideLayout(this, drawer))
        initNavigation()
    }

    open fun initNavigation() {
        navigation.setTitle("首页")
        navigation.addListener(
                object : NavigationBar.NavigationListener {
                    override fun onButtonClick(button: NavigationTag): Boolean {
                        when (button) {
                            NavigationTag.LEFT_VIEW -> openLeft()
                        }
                        return false
                    }
                })
    }


}