package com.intro.hao.mytools.net

import rx.Subscriber

/**
 * Created by haozhang on 2017/12/28.
 */
abstract class FileSubscribe<T> : Subscriber<T>() {
    override fun onStart() {
        super.onStart()
    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable) {

    }

    override fun onNext(t: T) {

    }

    fun onLoading(total: Long, progress: Long) {}

}