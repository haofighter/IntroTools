package com.intro.project.secret

import android.app.NotificationManager
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.intro.hao.mytools.Utils.animalUtil.AnimalUtils
import com.intro.hao.mytools.Utils.SharePreferenceUtils
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.Utils.animalUtil.AnimalUtilsModel
import com.intro.hao.mytools.base.App
import com.intro.project.secret.TestAcivity.TestActivity
import com.intro.project.secret.base.DrawarBaseActiivty
import com.intro.project.secret.moudle.WelcomeActivity
import kotlinx.android.synthetic.main.activity_secret_main.*
import android.app.PendingIntent
import android.content.Context
import android.support.v4.app.NotificationCompat


class MainActivity : DrawarBaseActiivty(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.main_fab -> {
                SharePreferenceUtils.get().clear()
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
            R.id.testAnimal -> {
                startActivity(Intent(this, TestActivity::class.java))
            }

            R.id.main_left -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.left)).addAlphaAnimal(false).addScaleAnimal(false).addTranAnimal(false).startAnimal()
                } else {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.left)).addAlphaAnimal(true).addScaleAnimal(true).addTranAnimal(true).startAnimal()
                }
            }
            R.id.main_right -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.right)).addAlphaAnimal(false).addScaleAnimal(false).addTranAnimal(false).startAnimal()
                } else {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.right)).addAlphaAnimal(true).addScaleAnimal(true).addTranAnimal(true).startAnimal()
                }
            }
            R.id.main_top -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.top)).addAlphaAnimal(false).addScaleAnimal(false).addTranAnimal(false).startAnimal()
                } else {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.top)).addAlphaAnimal(true).addScaleAnimal(true).addTranAnimal(true).startAnimal()
                }
            }
            R.id.main_bottom -> {
                if (testAnimal.visibility == View.VISIBLE) {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.bottom)).addAlphaAnimal(false).addScaleAnimal(false).addTranAnimal(false).startAnimal()
                } else {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(testAnimal, AnimalUtils.AnimalType.bottom)).addAlphaAnimal(true).addScaleAnimal(true).addTranAnimal(true).startAnimal()
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        getDrawerContentView().setBackgroundResource(R.color.white)
        main_left.setOnClickListener(this)
        main_right.setOnClickListener(this)
        main_top.setOnClickListener(this)
        main_bottom.setOnClickListener(this)
        testAnimal.setOnClickListener(this)

        SharePreferenceUtils.get().setDate("userId", 1L)
        sendSimplestNotificationWithAction()

//        var searchInfo: RealmResults<NoteInfo> = Realm.getDefaultInstance().where(NoteInfo::class.java).equalTo("userID", 0 as Int).findAll()
//        searchInfo.addChangeListener((
//                object : RealmChangeListener<RealmResults<NoteInfo>> {
//                    override fun onChange(results: RealmResults<NoteInfo>?) {
//                        Log.i("查询", " " + results.toString())
//                    }
//                }))

    }

    override fun LayoutID(): Int {
        return R.layout.activity_secret_main
    }


    var finishTime: Long = 0
    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - finishTime > 2000) {
                finishTime = System.currentTimeMillis()
                ToastUtils().showMessage("再次点击返回键退出APP")
            } else {
                App.instance.finishActivty()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    /**
     * 发送一个点击跳转到MainActivity的消息
     */
    private fun sendSimplestNotificationWithAction() {
        var notifyManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //获取PendingIntent
        val mainIntent = Intent(this, MainActivity::class.java)
        val mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //创建 Notification.Builder 对象
        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setContentIntent(mainPendingIntent).build()
        //发送通知
        notifyManager.notify(1, builder)
    }


}
