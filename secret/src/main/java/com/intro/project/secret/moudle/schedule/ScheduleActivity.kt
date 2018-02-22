package com.intro.project.secret.moudle.schedule

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.intro.hao.mytools.Utils.DataUtils
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.nCanander.calendar.NCalendar
import com.intro.hao.nCanander.listener.OnCalendarChangedListener
import com.intro.project.secret.R
import com.intro.project.secret.base.DrawarBaseActiivty
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.schedule_tool_bar.*
import org.joda.time.DateTime

/**
 * 日程计划
 */
class ScheduleActivity : DrawarBaseActiivty(), View.OnClickListener, OnCalendarChangedListener {


    lateinit var scheduleAdapter: ScheduleAdapter

    override fun LayoutID(): Int {
        return R.layout.activity_schedule
    }

    //页面点击事件
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.schedule_mune -> openLeft()
            R.id.schedule_data -> showCalander()
        }
    }

    override fun onCalendarChanged(dateTime: DateTime?) {
        Log.i("dateTime", dateTime.toString())
    }

    //显示日历
    private fun showCalander() {
        if (ncalender.state == NCalendar.MONTH) {
            ncalender.toWeek(object : BackCall() {
                override fun deal(tag: Any, vararg obj: Any) {}

                override fun deal() {
                    tool_month.text = "周"
                }
            })
        } else {
            ncalender.toMonth(object : BackCall() {
                override fun deal(tag: Any, vararg obj: Any) {}

                override fun deal() {
                    tool_month.text = "月"
                }
            })
        }
    }

    //重新填充navigtionbar
    fun overrideNavBar() {
        navigation.setOurSelfAll(LayoutInflater.from(this).inflate(R.layout.schedule_tool_bar, null, false))
        navigation.clearListener()
        schedule_mune.setOnClickListener(this)
        schedule_data.setOnClickListener(this)
    }

    override fun initView() {
        super.initView()
        getDrawerContentView().setBackgroundResource(R.color.white)
        overrideNavBar()
        if (viewpager.adapter == null) {
            scheduleAdapter = ScheduleAdapter(this, backCall)
            viewpager.adapter = scheduleAdapter
        } else {
            scheduleAdapter = viewpager.adapter as ScheduleAdapter
        }
        viewpager.currentItem = Int.MAX_VALUE / 2
        ncalender.setOnCalendarChangedListener(this)
    }

    var backCall = object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
            when (tag) {
                "navbar" -> navigation.setTitle(DataUtils().getMonthDay(scheduleAdapter.showDate + obj[0] as Int * 24 * 60 * 60 * 1000))
            }
        }
    }


}


class ScheduleAdapter : PagerAdapter {
    var context: Context
    var backCall: BackCall

    var showDate: Long = System.currentTimeMillis()

    constructor(context: Context, backCall: BackCall) {
        this.context = context
        this.backCall = backCall
    }


    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.schedule_content_layout, container, false)
        backCall.deal("navbar", position - Int.MAX_VALUE / 2)
        when (position % 3) {
            0 -> view.findViewById<LinearLayout>(R.id.schedule_content).setBackgroundResource(R.color.Blue)
            1 -> view.findViewById<LinearLayout>(R.id.schedule_content).setBackgroundResource(R.color.Green)
            2 -> view.findViewById<LinearLayout>(R.id.schedule_content).setBackgroundResource(R.color.Orange)
        }
        container!!.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {
//        super.destroyItem(container, position, `object`)
        container!!.removeView(obj as View)
    }


}