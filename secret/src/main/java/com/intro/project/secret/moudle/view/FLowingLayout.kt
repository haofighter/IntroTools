package com.intro.project.secret.moudle.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.intro.project.secret.R
import com.intro.project.secret.moudle.MainTestKeyboradActivity
import com.intro.project.secret.moudle.music.MusicHomeActivity
import com.intro.project.secret.moudle.note.EditNoteActivity
import kotlinx.android.synthetic.main.flowing_layout.view.*

/**
 * 自定义侧滑的界面
 * 包含界面 及逻辑跳转
 */
class FLowingLayout : LinearLayout, View.OnClickListener {
    var mContext: Context?
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.note -> mContext!!.startActivity(Intent(context, EditNoteActivity::class.java))
            R.id.music -> mContext!!.startActivity(Intent(context, MusicHomeActivity::class.java))
            R.id.test -> mContext!!.startActivity(Intent(context, MainTestKeyboradActivity::class.java))
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
        addView(content)
        note.setOnClickListener(this)
        music.setOnClickListener(this)
        test.setOnClickListener(this)
    }

}