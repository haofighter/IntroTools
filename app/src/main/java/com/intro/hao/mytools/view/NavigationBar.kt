package com.intro.hao.mytools.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
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
        fun onButtonClick(button: NavigationTag)
    }

    private var mListener: NavigationListener? = null

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

    fun setListener(listener: NavigationListener) {
        this.mListener = listener
    }

    internal var buttonListener: View.OnClickListener = View.OnClickListener { v ->
        if (v.id == R.id.left_layout) {
            if (mListener != null) {
                mListener!!.onButtonClick(NavigationTag.LEFT_VIEW)
            }
        }
        if (v.id == R.id.right) {
            if (mListener != null) {
                mListener!!.onButtonClick(NavigationTag.RIGHT_VIEW)
            }
        }

        if (v.id == R.id.center) {
            if (mListener != null) {
                mListener!!.onButtonClick(NavigationTag.MIDDLE_VIEW)
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