package com.intro.hao.mytools

import android.Manifest
import android.app.AlarmManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.intro.hao.mytools.ResponseBean.BaseResponce
import com.intro.hao.mytools.Utils.PromisionUtils
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.net.RetrofitManager
import com.intro.hao.mytools.net.Service
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.intro.hao.mytools.constant.AppConstant
import com.intro.hao.mytools.home.activity.HomeActivity
import com.intro.hao.mytools.home.receiver.AlarmReceiver
import jp.wasabeef.glide.transformations.BlurTransformation
import com.intro.hao.mytools.home.service.HorizonService


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.set_a_clock -> {
                ToastUtils().showMessage("设置定时任务")
                var intent = Intent(this, HorizonService::class.java)
                intent.putExtra(AppConstant().HORIZENSERVICE_TIME, System.currentTimeMillis() + 2000)
                startService(intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (PromisionUtils(this).ApplyPromise(this, Manifest.permission.INTERNET, 0)) {
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


    override fun onResume() {
        super.onResume()
    }
}


