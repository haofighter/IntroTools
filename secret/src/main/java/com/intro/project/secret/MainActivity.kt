package com.intro.project.secret

import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.intro.hao.mytools.Utils.AnimalUtils
import com.intro.hao.mytools.Utils.KeyboardChangeListener
import com.intro.hao.mytools.Utils.SharePreferenceUtils
import com.intro.hao.mytools.base.App
import com.intro.project.secret.TestAcivity.TestActivity
import com.intro.project.secret.base.BaseActiivty
import com.intro.project.secret.moudle.WelcomeActivity
import com.intro.project.secret.moudle.note.modle.NoteInfo
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActiivty(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                SharePreferenceUtils.get().clear()
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            R.id.testAnimal -> {
                startActivity(Intent(this, TestActivity::class.java))
            }

            R.id.left -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setDissmisAnimal(testAnimal, AnimalUtils.AnimalType.left, null)
                } else {
                    AnimalUtils().setShowAnimal(testAnimal, AnimalUtils.AnimalType.left, null)
                }
            }
            R.id.right -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setDissmisAnimal(testAnimal, AnimalUtils.AnimalType.right, null)
                } else {
                    AnimalUtils().setShowAnimal(testAnimal, AnimalUtils.AnimalType.right, null)
                }
            }
            R.id.top -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setDissmisAnimal(testAnimal, AnimalUtils.AnimalType.top, null)
                } else {
                    AnimalUtils().setShowAnimal(testAnimal, AnimalUtils.AnimalType.top, null)
                }
            }
            R.id.bottom -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setDissmisAnimal(testAnimal, AnimalUtils.AnimalType.bottom, null)
                } else {
                    AnimalUtils().setShowAnimal(testAnimal, AnimalUtils.AnimalType.bottom, null)
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        fab.setOnClickListener(this)
        left.setOnClickListener(this)
        right.setOnClickListener(this)
        top.setOnClickListener(this)
        bottom.setOnClickListener(this)
        testAnimal.setOnClickListener(this)

        SharePreferenceUtils.get().setDate("userId", 1L)

        Log.i("查询", " " + NoteInfo().queryAll().size)
    }

    override fun LayoutID(): Int {
        setKeyboardChangeListener(object : KeyboardChangeListener.KeyBoardListener {
            override fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int) {
                Log.d("软键盘的监听", "isShow = [$isShow], keyboardHeight = [$keyboardHeight]")
            }
        })
        return R.layout.activity_main
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            App.instance.finishActivty()
        }
        return super.onKeyDown(keyCode, event)
    }
}
