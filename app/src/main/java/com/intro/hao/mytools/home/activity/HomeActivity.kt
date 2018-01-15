package com.intro.hao.mytools.home.activity

import android.app.Activity
import android.os.Bundle
import com.intro.hao.mytools.R
import com.intro.hao.mytools.base.ToolBarBaseActivity
import com.intro.hao.mytools.view.NavigationBar
import com.intro.hao.mytools.view.NavigationTag

/**
 * Created by haozhang on 2018/1/3.
 */
class HomeActivity : ToolBarBaseActivity() {
    override fun LayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        navigation.setLeftText("侧滑")
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag) {
                when (button) {
                    NavigationTag.LEFT_VIEW -> {
                        openSiding()
                    }
                    NavigationTag.MIDDLE_VIEW -> {
                    }
                    NavigationTag.RIGHT_VIEW -> {
                    }
                }
            }
        })
    }


}