package com.intro.hao.mytools.customview

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.intro.hao.mytools.R
import com.intro.hao.mytools.base.App
import kotlinx.android.synthetic.main.navigation_tool_bar.view.*

/**
 * Created by hao on 2018/2/6.
 */
class NavigationBar : RelativeLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    fun initView() {
        LayoutInflater.from(App.instance).inflate(R.layout.navigation_tool_bar, this, true)
        left_base_layout.setOnClickListener(buttonListener)
        navigation_base_center.setOnClickListener(buttonListener)
        right_base_layout.setOnClickListener(buttonListener)
    }

    interface NavigationListener {
        //返回false  表示允许执行后续添加的监听
        fun onButtonClick(button: NavigationTag): Boolean
    }

    private var mListener: NavigationListener? = null
    private var addListener: MutableList<NavigationListener> = mutableListOf()

    fun setOurSelfLeft(v: View) {
        left_base_layout!!.removeAllViews()
        left_base_layout!!.addView(v)
    }

    fun setOurSelfCenter(v: View) {
        navigation_base_center!!.removeAllViews()
        navigation_base_center!!.addView(v)
    }

    fun setOurSelfRight(v: View) {
        right_base_layout!!.removeAllViews()
        right_base_layout!!.addView(v)
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

    fun clearListener() {
        addListener.clear()
    }

    internal var buttonListener: View.OnClickListener = View.OnClickListener { v ->
        if (v.id == R.id.left_base_layout) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.LEFT_VIEW))
                    break
            }
        }
        if (v.id == R.id.right_base_layout) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.RIGHT_VIEW))
                    break
            }
        }

        if (v.id == R.id.navigation_base_center) {
            for (it in addListener) {
                if (it.onButtonClick(NavigationTag.MIDDLE_VIEW))
                    break
            }
        }
    }


    fun setLeftImage(resId: Int) {
        tv_navigation_base_left!!.visibility = View.GONE
        img_navigation_base_left!!.visibility = View.VISIBLE
        img_navigation_base_left!!.setImageResource(resId)
    }

    fun setLeftBack(activity: Activity) {
        tv_navigation_base_left!!.visibility = View.GONE
        img_navigation_base_left!!.visibility = View.VISIBLE
        img_navigation_base_left!!.setOnClickListener { activity.finish() }
    }

    fun setLeftText(string: String) {
        img_navigation_base_left!!.visibility = View.GONE
        img_navigation_base_left!!.visibility = View.VISIBLE
        tv_navigation_base_left!!.text = string
    }


    fun setTitle(string: String) {
        tv_navigation_base_title!!.text = string
    }

    fun setTitleColor(color: Int) {
        tv_navigation_base_title!!.setTextColor(color)
    }

    fun setTitle(strResId: Int) {
        tv_navigation_base_title!!.setText(strResId)
    }

    fun setRightText(string: String) {
        img_navigation_base_right!!.visibility = View.GONE
        tv_navigation_base_right!!.visibility = View.VISIBLE
        tv_navigation_base_right!!.text = string
    }

    fun setRightTextColor(color: Int) {
        tv_navigation_base_right!!.setTextColor(ContextCompat.getColor(getContext(), color))
    }


    fun getRightText(): String {
        return tv_navigation_base_right!!.text.toString()
    }

    fun setRightButton(resId: Int) {
        tv_navigation_base_right!!.visibility = View.INVISIBLE
        img_navigation_base_right!!.visibility = View.VISIBLE
        img_navigation_base_right!!.setImageResource(resId)
    }

}