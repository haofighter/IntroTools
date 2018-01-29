package com.intro.project.secret.base

import com.intro.hao.mytools.base.ToolBarBaseActivity
import com.intro.hao.mytools.view.NavigationBar
import com.intro.hao.mytools.view.NavigationTag
import com.intro.project.secret.moudle.view.FLowingLayout
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by haozhang on 2018/1/23.
 */
abstract class BaseActiivty : ToolBarBaseActivity() {
    override fun initView() {
        //初始化realm   并给定一个默认设置
        setFlowingDrawerContentView(FLowingLayout(this))
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