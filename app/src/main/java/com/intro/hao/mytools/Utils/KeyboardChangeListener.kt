package com.intro.hao.mytools.Utils

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver

/**
 * 针对软件盘的监听
 */
class KeyboardChangeListener : ViewTreeObserver.OnGlobalLayoutListener {
    private var mContentView: View? = null
    private var mOriginHeight: Int = 0
    private var mPreHeight: Int = 0
    private var mKeyBoardListen: KeyBoardListener? = null

    interface KeyBoardListener {
        /**
         * call back
         * @param isShow true is show else hidden
         * @param keyboardHeight keyboard height
         */
        fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int)
    }

    fun setKeyBoardListener(keyBoardListen: KeyBoardListener) {
        if (keyBoardListen != null)
            this.mKeyBoardListen = keyBoardListen
    }

    constructor(contextObj: Activity) {
        if (contextObj == null) {
            Log.i(TAG, "contextObj is null")
            return
        }
        mContentView = findContentView(contextObj)
        if (mContentView != null) {
            addContentTreeObserver()
        }
    }

    private fun findContentView(contextObj: Activity): View {
        return contextObj.findViewById(android.R.id.content)
    }

    private fun addContentTreeObserver() {
        mContentView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val currHeight = mContentView!!.height
        if (currHeight == 0) {
            Log.i(TAG, "currHeight is 0")
            return
        }
        var hasChange = false
        if (mPreHeight == 0) {
            mPreHeight = currHeight
            mOriginHeight = currHeight
        } else {
            if (mPreHeight != currHeight) {
                hasChange = true
                mPreHeight = currHeight
            } else {
                hasChange = false
            }
        }
        if (hasChange) {
            val isShow: Boolean
            var keyboardHeight = 0
            if (mOriginHeight == currHeight) {
                //hidden
                isShow = false
            } else {
                //show
                keyboardHeight = mOriginHeight - currHeight
                isShow = true
            }

            if (mKeyBoardListen != null) {
                mKeyBoardListen!!.onKeyboardChange(isShow, keyboardHeight)
            }
        }
    }

    fun destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    }

    companion object {
        private val TAG = "ListenerHandler"
    }
}