package com.intro.hao.mytools.home.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.intro.hao.mytools.R
import com.intro.hao.mytools.ResponseBean.BaseResponce
import com.intro.hao.mytools.Utils.KeyboardChangeListener
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.base.ToolBarBaseFlowingActivity
import com.intro.hao.mytools.constant.AppConstant
import com.intro.hao.mytools.net.RetrofitManager
import com.intro.hao.mytools.net.Service
import com.intro.project.clock.clockutils.SpecialBackCall
import com.intro.project.clock.service.AlarmService
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers


class MainFlowingActivity : ToolBarBaseFlowingActivity(), View.OnClickListener {
    override fun LayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setKeyboardChangeListener(object : KeyboardChangeListener.KeyBoardListener {
            override fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int) {
                Log.d("软键盘的监听", "isShow = [$isShow], keyboardHeight = [$keyboardHeight]")
            }
        })

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            RetrofitManager().builder(Service::class.java).getCarInfoObservable().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Action1<BaseResponce> {
                        override fun call(t: BaseResponce?) {
                            ToastUtils().showMessage(t.toString())
                        }

                    }, Action1<Throwable> { call ->
                        ToastUtils().showMessage(call.toString())
                        Log.i("请求错误", call.toString());
                    });
        }

        set_a_clock.setOnClickListener(this)
        Glide.with(this).load(R.mipmap.main1).bitmapTransform(BlurTransformation(this, 5)).into(image_show);
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.set_a_clock -> {
                var intent = Intent(this, AlarmService::class.java)
                intent.putExtra(AppConstant().BACK_CALL, object : SpecialBackCall(R.id.set_a_clock) {
                    override fun deal(vararg objects: Any) {
                        Log.i("tag", "定时任务回调启动了")
                        ToastUtils().showMessage("定时任务回调启动了·")
                    }
                })
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, System.currentTimeMillis())
                intent.putExtra(AppConstant().ALAERN_TAG, R.id.set_a_clock)
                startService(intent)
            }
        }
    }
}


