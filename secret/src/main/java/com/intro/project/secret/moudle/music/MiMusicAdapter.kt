package com.intro.project.secret.moudle.music

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intro.hao.mytools.base.BackCall
import com.intro.project.secret.R

/**
 * Created by haozhang on 2018/1/25.
 */

class MiMusicAdapter : Adapter<RecyclerView.ViewHolder> {
    override fun getItemCount(): Int {
        if (mutableList.size == 0) {
            return 0
        } else {
            return 1
        }
    }

    private var context: Context
    private var backCall: BackCall?

    var mutableList: MutableList<String> = mutableListOf()
    fun setDate(mutableList: MutableList<String>) {
        this.mutableList = mutableList
    }


    constructor(context: Context, backCall: BackCall) {
        this.context = context
        this.backCall = backCall
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {//表示没有歌曲
            return EmptyMusicHolder(LayoutInflater.from(context).inflate(R.layout.empty_music_item, parent, false))
        } else if (viewType == 1) {
            return MusicHolder(LayoutInflater.from(context).inflate(R.layout.empty_music_item, parent, false))
        } else {
            return EmptyMusicHolder(LayoutInflater.from(context).inflate(R.layout.empty_music_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }


    class EmptyMusicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    class MusicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}