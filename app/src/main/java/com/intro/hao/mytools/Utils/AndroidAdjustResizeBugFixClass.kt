package com.intro.hao.mytools.Utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout

/**
 * Created by haozhang on 2018/1/22.
 */
class AndroidAdjustResizeBugFixClass {
    private var mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private var statusBarHeight: Int
    private var frameLayoutParams: FrameLayout.LayoutParams;
    private var mActivity: Activity

    constructor(activity: Activity) {
        mActivity = activity;
        var content = (activity.findViewById<FrameLayout>(android.R.id.content));
        mChildOfContent = content.getChildAt(0);
        statusBarHeight = getStatusBarHeightMi();
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                possiblyResizeChildOfContent();
            }

        })
        frameLayoutParams = mChildOfContent.getLayoutParams() as FrameLayout.LayoutParams

    }

    fun possiblyResizeChildOfContent() {
        var usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            var usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            var heightDifference = usableHeightSansKeyboard - usableHeightNow;if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                // 如果有高度变化，mChildOfContent.requestLayout()之后界面才会重新测量
                // 这里就随便让原来的高度减去了1
                frameLayoutParams.height = usableHeightSansKeyboard - 1;

            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;

            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }

    }

    fun computeUsableHeight(): Int {
        var r = Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top + statusBarHeight;

    }

    private fun getStatusBarHeightMi(): Int {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            return mActivity.getResources().getDimensionPixelSize(x)

        } catch (e: Exception) {
            e.printStackTrace()

        }
        return 0
    }


}