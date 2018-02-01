package com.intro.project.secret.moudle.music

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.Utils.listener.SearchFileLisener
import com.intro.hao.mytools.base.BackCall
import com.intro.project.secret.R
import com.intro.project.secret.base.BaseActiivty
import kotlinx.android.synthetic.main.activity_music_home.*

class MusicHomeActivity : BaseActiivty() {
    override fun LayoutID(): Int {
        return R.layout.activity_music_home
    }

    var musicSearch: MutableList<String>? = null
    var showTag = 0

    override fun initView() {
        super.initView()
        navigation.setTitle("我的music")
        //设置布局管理器
        mi_music.setLayoutManager(LinearLayoutManager(this))
        //设置增加或删除条目的动画
        mi_music.setItemAnimator(DefaultItemAnimator())
        mi_music.adapter = MiMusicAdapter(this, backCall)

        SystemUtils().searchFlie(mutableListOf<String>(".mp3", ".wma", ".wav", ".asf", ".aac", ".mp3pro", "avi"), object : SearchFileLisener {
            override fun updateProgress(vararg values: String?) {
            }

            override fun finish(result: MutableList<String>?) {
                musicSearch = result
            }

            override fun onCancal() {
            }

            override fun onStart() {
            }
        })
    }

    fun setShowMusic() {
        (mi_music.adapter as MiMusicAdapter).addDate(musicSearch!!.subList(0, (showTag + 1) * 10))
    }


    var backCall = object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
        }
    }

}
