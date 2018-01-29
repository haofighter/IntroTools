package com.intro.project.secret.moudle

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.intro.hao.mytools.Utils.KeyboardChangeListener
import com.intro.project.secret.R
import com.intro.project.secret.base.BaseDrawerActivity

class MainTestKeyboradActivity : BaseDrawerActivity() {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test_keyborad)

        KeyboardChangeListener(this).setKeyBoardListener(
                object : KeyboardChangeListener.KeyBoardListener {
                    override fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int) {
                        Log.d("软键盘的监听", "isShow = [$isShow], keyboardHeight = [$keyboardHeight]")
                    }
                })

    }

}
