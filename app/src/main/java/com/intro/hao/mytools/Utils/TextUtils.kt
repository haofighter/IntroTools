package com.intro.hao.mytools.Utils

import android.graphics.Paint
import android.support.annotation.ColorInt
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.widget.TextView

/**
 * Created by haozhang on 2018/1/3.
 */
class TextUtils {
    fun setSelectChangeColor(tv: TextView, @ColorInt color: Int, start: Int, end: Int, str: String) {
        if (start > str.length - 1 || end > str.length || start >= end || start < 0 || end < 0) {
        }
        val msp = SpannableString(str)
        msp.setSpan(BackgroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv.text = msp
    }

    fun setCenterLine(tv: TextView) {
        tv.paint.isAntiAlias = true//抗锯齿
        tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
        tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG // 设置中划线并加清晰
    }

    fun setBottomLine(tv: TextView) {
        tv.paint.isAntiAlias = true//抗锯齿
        tv.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG // 设置中划线并加清晰
    }

    fun deleteLine(tv: TextView) {
        tv.paint.flags = 0
    }
}