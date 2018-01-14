package com.intro.hao.mytools.net

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * Created by haozhang on 2017/12/28.
 */
class FileResponseBody constructor(): ResponseBody() {

    /**
     * 实际请求体
     */
    private lateinit var mResponseBody: ResponseBody
    /**
     * 下载回调接口
     */
    private lateinit var mCallback: ProgressListener
    /**
     * 下载回调接口
     */
    private lateinit var fileSubscribe: FileSubscribe<Any>
    /**
     * BufferedSource
     */
    private lateinit var mBufferedSource: BufferedSource

    constructor(mResponseBody: ResponseBody, mCallback: ProgressListener) : this() {
        this.mResponseBody = mResponseBody
        this.mCallback = mCallback
    }

    constructor(body: ResponseBody?, callback: FileSubscribe<Any>) : this() {
        this.mCallback = mCallback
        this.fileSubscribe = fileSubscribe
    }


    override fun contentLength(): Long {
        return mResponseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return mResponseBody.contentType()
    }


    override fun source(): BufferedSource {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()))
        }
        return mBufferedSource
    }


    /**
     * 回调进度接口
     * @param source
     * @return Source
     */
    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                var bytesRead: Long = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0;
                if (mCallback != null)
                    mCallback.onProgress(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1L)
                if (fileSubscribe != null) {
                    fileSubscribe.onLoading(mResponseBody.contentLength(), totalBytesRead)
                }
                return bytesRead
            }
        }
    }



}
