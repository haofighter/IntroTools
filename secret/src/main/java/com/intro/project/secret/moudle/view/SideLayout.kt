package com.intro.project.secret.moudle.view

import android.content.Context
import android.content.Intent
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.intro.hao.mytools.Utils.ReflectUtils
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.base.BackCall
import com.intro.project.secret.R
import com.intro.project.secret.TestAcivity.TestActivity
import com.intro.project.secret.moudle.music.MusicHomeFActivity
import com.intro.project.secret.moudle.note.EditNoteActivity
import com.intro.project.secret.moudle.note.ShowNoteListActivity
import com.intro.project.secret.moudle.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.flowing_layout.view.*

/**
 * 自定义侧滑的界面
 * 包含界面 及逻辑跳转
 */
class SideLayout : RelativeLayout, View.OnClickListener {
    var mContext: Context?
    var drawerLayout: DrawerLayout?
    var v: View? = null
    override fun onClick(v: View?) {
        this.v = v
        if (drawerLayout != null) { //设置此布局后  侧滑关闭后触发
//使用反射来清楚listenner 性能影响过大  卡顿
//            var filed = ReflectUtils().printClassMethodMessage(drawerLayout!!).getDeclaredField("mListeners")
//            filed.setAccessible(true);
//            filed.set(drawerLayout, mutableListOf<DrawerLayout.DrawerListener>())
            drawerLayout!!.closeDrawers()
        }
    }


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, drawerLayout: DrawerLayout?) : this(context, null, drawerLayout)
    constructor(context: Context?, attrs: AttributeSet?, drawerLayout: DrawerLayout?) : this(context, attrs, 0, drawerLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, drawerLayout: DrawerLayout?) : super(context, attrs, defStyleAttr) {
        mContext = context
        initView(context)
        this.drawerLayout = drawerLayout
        if (drawerLayout == null) {
            Log.e("errer", "需要指定一个关联的侧滑布局")
            return
        }
        drawerLayout!!.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View?) {
                if (v == null) return
                else {
                    when (v!!.id) {
                        R.id.note -> mContext!!.startActivity(Intent(context, ShowNoteListActivity::class.java))

                        R.id.music -> mContext!!.startActivity(Intent(context, MusicHomeFActivity::class.java))

                        R.id.test -> mContext!!.startActivity(Intent(context, TestActivity::class.java))

                        R.id.plan -> mContext!!.startActivity(Intent(context, ScheduleActivity::class.java))

                    }
                    v = null//将v重置为空  防止后续直接关闭侧滑时出现跳转
                }
            }

            override fun onDrawerOpened(drawerView: View?) {
            }
        })
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
        plan.setOnClickListener(this)
    }

}