package com.intro.project.secret.moudle.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.project.secret.R
import com.intro.project.secret.TestAcivity.TestActivity
import com.intro.project.secret.moudle.music.MusicHomeFlowingBaseActivity
import com.intro.project.secret.moudle.note.EditNoteActivity
import kotlinx.android.synthetic.main.flowing_layout.view.*

/**
 * 自定义侧滑的界面
 * 包含界面 及逻辑跳转
 */
class SideLayout : RelativeLayout, View.OnClickListener {
    var mContext: Context?
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.note -> mContext!!.startActivity(Intent(context, EditNoteActivity::class.java))
            R.id.music -> mContext!!.startActivity(Intent(context, MusicHomeFlowingBaseActivity::class.java))
            R.id.test -> mContext!!.startActivity(Intent(context, TestActivity::class.java))
        }
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        initView(context)
    }

    fun initView(context: Context?) {
        var content = LayoutInflater.from(context).inflate(R.layout.flowing_layout, null)
        Log.i("控件里面的宽度", "" + SystemUtils().getViewSize(content).measuredWidth)
        layoutParams = LayoutParams(SystemUtils().getViewSize(content).measuredWidth, RelativeLayout.LayoutParams.MATCH_PARENT)
        addView(content)
        Log.i("控件里面的宽度", "" + SystemUtils().getViewSize(this).measuredWidth)
        note.setOnClickListener(this)
        music.setOnClickListener(this)
        test.setOnClickListener(this)
    }

}