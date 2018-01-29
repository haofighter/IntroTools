package com.intro.hao.mytools.view

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.intro.hao.mytools.R
import kotlinx.android.synthetic.main.navigation_bar.view.*

/**
 * 使用kotlin 语法构建toolbar
 */
class NavigationBar : RelativeLayout {


    interface NavigationListener {
        //返回false  表示允许执行后续添加的监听
        fun onButtonClick(button: NavigationTag): Boolean
    }

    private var mListener: NavigationListener? = null
    private var addListener: MutableList<NavigationListener> = mutableListOf()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(getContext()).inflate(R.layout.navigation_bar, this, true)
        left_layout.setOnClickListener(buttonListener)
        center!!.setOnClickListener(buttonListener)
        right_layout!!.setOnClickListener(buttonListener)
    }

    fun setOurSelfLeft(v: View) {
        left_layout!!.removeAllViews()
        left_layout!!.addView(v)
    }

    fun setOurSelfCenter(v: View) {
        center!!.removeAllViews()
        center!!.addView(v)
    }

    fun setOurSelfRight(v: View) {
        right_layout!!.removeAllViews()
        right_layout!!.addView(v)
    }

    fun setOurSelfAll(v: View) {
        bar_main_layout.removeAllViews()
        bar_main_layout.addView(v)
    }


    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
    }

    fun addListener(listener: NavigationListener) {
        addListener.add(listener)
    }

    fun setListener(listener: NavigationListener) {
        addListener.clear()
        addListener.add(listener)
    }

    fun clearListener(listener: NavigationListener) {
        addListener.clear()
    }

    internal var buttonListener: View.OnClickListener = View.OnClickListener { v ->
        Log.i("view的id", "" + v.id + "  " + R.id.right_layout + "   " + R.id.tv_navigation_right)
        if (v.id == R.id.left_layout) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.LEFT_VIEW))
                    break
            }
        }
        if (v.id == R.id.right_layout) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.RIGHT_VIEW))
                    break
            }
        }

        if (v.id == R.id.center) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.MIDDLE_VIEW))
                    break
            }
        }
    }


    fun setLeftImage(resId: Int) {
        tv_navigation_left!!.visibility = View.GONE
        img_navigation_left!!.visibility = View.VISIBLE
        img_navigation_left!!.setImageResource(resId)
    }

    fun setLeftBack(activity: Activity) {
        tv_navigation_left!!.visibility = View.GONE
        img_navigation_left!!.visibility = View.VISIBLE
        img_navigation_left!!.setOnClickListener { activity.finish() }
    }

    fun setLeftText(string: String) {
        img_navigation_left!!.visibility = View.GONE
        tv_navigation_left!!.visibility = View.VISIBLE
        tv_navigation_left!!.text = string
    }


    fun setTitle(string: String) {
        tv_navigation_title!!.text = string
    }

    fun setTitleColor(color: Int) {
        tv_navigation_title!!.setTextColor(color)
    }

    fun setTitle(strResId: Int) {
        tv_navigation_title!!.setText(strResId)
    }

    fun setRightText(string: String) {
        img_navigation_right!!.visibility = View.GONE
        tv_navigation_right!!.visibility = View.VISIBLE
        tv_navigation_right!!.text = string
    }

    fun setRightTextColor(color: Int) {
        tv_navigation_right!!.setTextColor(ContextCompat.getColor(getContext(), color))
    }


    fun getRightText(): String {
        return tv_navigation_right!!.text.toString()
    }

    fun setRightButton(resId: Int) {
        tv_navigation_right!!.visibility = View.INVISIBLE
        img_navigation_right!!.visibility = View.VISIBLE
        img_navigation_right!!.setImageResource(resId)
    }

}