package com.intro.project.secret.base

import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.base.ToolBarBaseFlowingActivity
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.project.secret.moudle.view.SideLayout


/**
 * Created by haozhang on 2018/1/23.
 */
abstract class FlowingBaseActiivty : ToolBarBaseFlowingActivity() {
    override fun initView() {
        BaseApp.instance.nowActivity = this
        //初始化realm   并给定一个默认设置
        setFlowingDrawerContentView(SideLayout(this))
        navigation.setTitle("首页")
        navigation.addListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.LEFT_VIEW -> openSiding()
                }
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}