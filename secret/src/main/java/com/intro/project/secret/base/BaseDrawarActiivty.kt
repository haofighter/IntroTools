package com.intro.project.secret.base

import com.intro.hao.mytools.base.BaseToolBarActivity
import com.intro.hao.mytools.view.NavigationBar
import com.intro.hao.mytools.view.NavigationTag
import com.intro.project.secret.moudle.view.SideLayout


/**
 * Created by haozhang on 2018/1/23.
 */
abstract class BaseDrawarActiivty : BaseToolBarActivity() {
    override fun initView() {
        //初始化realm   并给定一个默认设置
        setDrawerContentView(SideLayout(this))
        navigation.setTitle("首页")
        navigation.addListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.LEFT_VIEW -> openLeft()
                }
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}