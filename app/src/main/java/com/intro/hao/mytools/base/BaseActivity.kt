package com.intro.hao.mytools.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.DialogUtils
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.addActivty(this)
        App.instance.nowActivity = this
        super.setContentView(R.layout.activity_base)
    }

    fun setBaseContentView(layoutId: Int) {
        setBaseContentView(LayoutInflater.from(this).inflate(layoutId, null))
    }

    fun setBaseContentView(layout: View) {
        base_activity.removeAllViews()
        base_activity.addView(layout)
    }


    fun showLoading() {
        DialogUtils.instance.LoadingDialog(this, false).show()
    }

    fun closeLoading() {
        DialogUtils.instance.LoadingDialog(this, false).dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.removeActivty(this)
    }
}
