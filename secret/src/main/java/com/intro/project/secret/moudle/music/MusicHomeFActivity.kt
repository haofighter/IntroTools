package com.intro.project.secret.moudle.music

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.intro.hao.Twink.RefreshListenerAdapter
import com.intro.hao.Twink.TwinklingRefreshLayout
import com.intro.hao.mytools.Utils.MusicUtils
import com.intro.hao.mytools.Utils.listener.SearchMusicLisener
import com.intro.hao.mytools.Utils.modle.MusicInfo
import com.intro.hao.mytools.Utils.modle.VadioFileInfo
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.customview.RecycleViewDivider
import com.intro.project.secret.R
import com.intro.project.secret.base.FlowingBaseActiivty
import kotlinx.android.synthetic.main.activity_music_home.*

class MusicHomeFActivity : FlowingBaseActiivty() {


    var musicSearch: MutableList<VadioFileInfo> = mutableListOf()
    var showTag = 0

    override fun LayoutID(): Int {
        return R.layout.activity_music_home
    }

    override fun initView() {
        super.initView()
        navigation.setTitle("我的音乐")
        initRefresh()
        setBaseBackGround(R.mipmap.main1)
//        navigation.setTitle("我的music")
        //设置布局管理器
        mi_music.setLayoutManager(LinearLayoutManager(this))
        //设置增加或删除条目的动画
        mi_music.setItemAnimator(DefaultItemAnimator())
        //设置分界线
        mi_music.addItemDecoration(RecycleViewDivider(this, DividerItemDecoration.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.gray_22)))
        mi_music.adapter = MiMusicAdapter(this, backCall)
        mi_music.adapter.notifyDataSetChanged()
    }


    fun initRefresh() {
        refresh.setEnableRefresh(false)
        refresh.setEnableLoadmore(false)
        refresh.setOverScrollTopShow(false)
        refresh.setEnableOverScroll(false)//禁止界面回弹  可去掉刷新效果
        refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
                super.onLoadMore(refreshLayout)
                showTag++
                setShowMusic()
                refresh.finishLoadmore()
            }

            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                showTag = 0
                setShowMusic()
                refresh.finishRefreshing()
            }
        })

    }

    override fun onResume() {
        super.onResume()
//        musicSearch = VadioFileInfo().queryAll().toMutableList()
        setShowMusic()
    }


    fun searchMusic() {
        MusicUtils().searchFlie(object : SearchMusicLisener {
            override fun finish(result: MutableList<MusicInfo>?) {
                musicSearch = result as MutableList<VadioFileInfo>
//                musicSearch.saveAll()
                if (musicSearch.size > 0)
                    setShowMusic()
            }

            override fun onCancal() {
            }

            override fun onStart() {
            }

            override fun updateProgress(vararg values: String?) {
                runOnUiThread(object : Runnable {
                    override fun run() {
                        if (values.size > 0 && values[0] != null) {
                            (mi_music.adapter as MiMusicAdapter).searchFileName = values[0]
                            mi_music.adapter.notifyDataSetChanged()
                        }
                    }
                })
            }
        })
    }

    fun setShowMusic() {
        (mi_music.adapter as MiMusicAdapter).setDate(musicSearch.subList(0, if ((showTag + 1) * 10 > musicSearch.size) musicSearch.size else (showTag + 1) * 10))

        //如果列表不为空 开启刷新设置
        if ((mi_music.adapter as MiMusicAdapter).getDate()!!.size > 0) {
            refresh.setEnableRefresh(true)
            refresh.setEnableLoadmore(true)
        }
    }


    var backCall = object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
            when (tag) {
                R.id.music_empty_to_search -> searchMusic()
            }

        }
    }

}
