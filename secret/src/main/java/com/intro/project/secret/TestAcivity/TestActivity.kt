package com.intro.project.secret.TestAcivity

import com.intro.hao.mytools.base.BaseToolBarActivity
import com.intro.hao.mytools.view.NavigationBar
import com.intro.hao.mytools.view.NavigationTag
import com.intro.project.secret.R
import com.intro.project.secret.moudle.view.SideLayout

class TestActivity : BaseToolBarActivity() {
    override fun initView() {
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

    }

    override fun LayoutID(): Int {
        return R.layout.activity_test
    }

}
