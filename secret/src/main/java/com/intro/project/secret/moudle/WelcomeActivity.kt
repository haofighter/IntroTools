package com.intro.project.secret.moudle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.KeyEvent
import com.intro.hao.guide.PaperOnboardingFragment
import com.intro.hao.guide.PaperOnboardingPage
import com.intro.hao.mytools.Utils.SharePreferenceUtils
import com.intro.hao.mytools.base.App
import com.intro.project.secret.MainActivity
import com.intro.project.secret.R


class WelcomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isUsed = SharePreferenceUtils.get().getDate("isFristuse")
        if (!if (isUsed != null) isUsed as Boolean else false) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_welcome)
        initGuide()
    }

    fun setGuide(): ArrayList<PaperOnboardingPage> {
        val scr1 = PaperOnboardingPage("",
                "",
                Color.parseColor("#678FB4"), R.mipmap.main1, R.mipmap.icon1)
        val scr2 = PaperOnboardingPage("",
                "",
                Color.parseColor("#65B0B4"), R.mipmap.main10, R.mipmap.icon2)
        val scr3 = PaperOnboardingPage("",
                "",
                Color.parseColor("#9B90BC"), R.mipmap.main11, R.mipmap.icon3)

        val elements = mutableListOf<PaperOnboardingPage>()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)
        return elements as ArrayList<PaperOnboardingPage>
    }

    fun initGuide() {
        val onBoardingFragment = PaperOnboardingFragment.newInstance(setGuide())
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.guide, onBoardingFragment)
        fragmentTransaction.commit()
        onBoardingFragment.setOnRightOutListener {
            SharePreferenceUtils.get().setDate("isFristuse", true)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
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


    override fun onResume() {
        super.onResume()

        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION), 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            Log.i("grantResults这里面是啥", "长度" + grantResults.size)
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("同意了", permissions[i] + "的权限申请")
                } else {
                    Log.i("拒绝了", permissions[i] + "的权限申请")
                }
            }
        }
    }


}
