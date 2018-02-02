package com.intro.hao.mytools.Utils

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.listener.SearchMusicLisener
import com.intro.hao.mytools.Utils.modle.MusicInfo
import com.intro.hao.mytools.base.App
import com.intro.hao.mytools.base.BackCall


/**
 * Created by haozhang on 2018/2/1.
 */
class MusicUtils {

    var searchFileAsyncTask: MusicAsyncTask? = null
    fun searchFlie(searchMusicLisener: SearchMusicLisener) {
        if (ActivityCompat.checkSelfPermission(App.instance, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            searchFileAsyncTask = MusicAsyncTask(searchMusicLisener)
            searchFileAsyncTask!!.execute()
        } else {
            if (App.instance.nowActivity != null) {
                DialogUtils.instance.showInfoDialog(App.instance.nowActivity!!, "提示", "您还未获取到相关的操作权限是,无法使用此功能/n是否进行申请", "申请", "取消", object : BackCall() {
                    override fun deal() {
                    }

                    override fun deal(tag: Any, vararg obj: Any) {
                        when (tag) {
                            R.id.confirm -> {
                                ActivityCompat.requestPermissions(App.instance.nowActivity!!, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
                            }

                        }
                    }
                })
            } else {
                throw NullPointerException("未获取到 当前的activity ,  请尝试调用此方法之前调用 App.instance.nowActivity = this(Activity)")

            }
        }
    }

    class MusicAsyncTask constructor(searchMusicLisener: SearchMusicLisener) : AsyncTask<MutableList<String>, String, MutableList<MusicInfo>>() {
        var listener = searchMusicLisener
        override fun doInBackground(vararg p0: MutableList<String>): MutableList<MusicInfo>? {
            if (isCancelled()) {//执行中断方法 cancalSearchFileAsyncTask后只是给task设置一个状态  并没有对task进行处理 需要在此位置进行处理
                return mutableListOf<MusicInfo>()
            }
            return MusicUtils().locationMusicInfo(object : BackCall() {
                override fun deal() {
                }

                override fun deal(tag: Any, vararg obj: Any) {
                    listener.updateProgress(obj[0] as String)
                }

            })
        }

        override fun onPreExecute() {
            super.onPreExecute()
            listener.onStart()
        }


        override fun onCancelled() {
            super.onCancelled()
            listener.onCancal()
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            listener.updateProgress(*values)
        }

        override fun onPostExecute(result: MutableList<MusicInfo>?) {
            super.onPostExecute(result)
            Log.i("进度", "总共搜索" + result!!.size)
            listener.finish(result)
        }
    }


    private fun locationMusicInfo(backCall: BackCall): MutableList<MusicInfo> {
        var musics = mutableListOf<MusicInfo>()
        var cursor = App.instance.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        var i = 0;
        var cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            cursor.moveToFirst();
            while (i < cursorCount) {
                var musicInfo = MusicInfo()
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                musicInfo.url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                //歌曲ID：MediaStore.Audio.Media._ID
                musicInfo.id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))

                //歌曲的名称 ：MediaStore.Audio.Media.TITL
                musicInfo.tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))


                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                musicInfo.album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                musicInfo.artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                //歌曲文件的名称：MediaStroe.Audio.Media.DISPLAY_NAME
                musicInfo.display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))

                //歌曲文件的发行日期：MediaStore.Audio.Media.YEAR
                musicInfo.year = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR))

                // 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                musicInfo.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))

                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                musicInfo.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                musics.add(musicInfo)
                backCall.deal("musicPath", "" + musicInfo.url)
                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }
        return musics
    }


    /**
     * 读取媒体文件
     * 如果文件损坏  将返回一个null
     */
    fun getMusicInfoFromPath(path: String): MusicInfo? {
        val mmr = MediaMetadataRetriever()//实例化MediaMetadataRetriever对象mmr
//        val file = File(path)//实例化File对象file，指定文件路径为/storage/sdcard/Music/music1.mp3
//        Log.i("搜索的路径", file.getAbsolutePath())
        var musicInfo = MusicInfo()
        try {
            mmr.setDataSource(path)//设置mmr对象的数据源为上面file对象的绝对路径
//            musicInfo.album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)//获得音乐专辑的标题
            musicInfo.artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)//获取音乐的艺术家信息
//            musicInfo.tilte = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)//获取音乐标题信息
//            musicInfo.mimetype = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)//获取音乐mime类型
//            musicInfo.duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()//获取音乐持续时间
//            musicInfo.bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)//获取音乐比特率，位率
//            musicInfo.year = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)//获取音乐的日期
        } catch (e: Exception) {
            mmr.release()
            Log.e("资源文件读取出错", "路径" + path)
            return null
        }
        mmr.release()
        return musicInfo
    }

    fun getMusicImg(url: String, backCall: BackCall) {
        object : Thread() {
            override fun run() {
                super.run()
                val selectedAudio = Uri.parse(url)
                val myRetriever = MediaMetadataRetriever()
                myRetriever.setDataSource(App.instance, selectedAudio) // the URI of audio file
                val artwork: ByteArray?
                artwork = myRetriever.embeddedPicture

                App.instance.nowActivity!!.runOnUiThread(object : Runnable {
                    override fun run() {
                        if (artwork != null) {
                            backCall.deal("image", BitmapFactory.decodeByteArray(artwork, 0, artwork.size))
                        } else {
                            backCall.deal("image", BitmapFactory.decodeResource(App.instance.resources, R.mipmap.icon1))
                        }
                    }
                })
            }
        }.start()
    }

}