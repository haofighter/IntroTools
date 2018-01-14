package com.intro.hao.mytools.net

/**
 * Created by haozhang on 2017/12/28.
 */
interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    abstract fun onProgress(progress: Long, total: Long, done: Boolean)
}