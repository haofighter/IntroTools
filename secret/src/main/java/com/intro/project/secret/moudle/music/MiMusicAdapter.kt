package com.intro.project.secret.moudle.music

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.intro.hao.mytools.Utils.modle.VadioFileInfo
import com.intro.hao.mytools.base.BackCall
import com.intro.project.secret.R

/**
 * Created by haozhang on 2018/1/25.
 */

class MiMusicAdapter : Adapter<RecyclerView.ViewHolder> {
    override fun getItemCount(): Int {
        return if (mutableList == null || mutableList.size == 0) 1 else mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (mutableList == null || mutableList.size == 0) {
            (holder as EmptyMusicHolder).setDate((if (searchFileName == null) "" else searchFileName)!!)
        } else {
            (holder as MusicHolder).setDate(mutableList.get(position)).music_more.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    backCall!!.deal(R.id.music_item_more, mutableList.get(position))
                }
            })
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (mutableList.size == 0) {
            return 0
        } else {
            return 1
        }
    }


    private var context: Context
    var searchFileName: String? = ""
    private var backCall: BackCall?

    var mutableList: MutableList<VadioFileInfo> = mutableListOf()
    fun setDate(mutableList: MutableList<VadioFileInfo>?) {
        if (mutableList == null) {
            this.mutableList = mutableListOf()
        } else {
            this.mutableList = mutableList
        }
        notifyDataSetChanged()
    }

    fun addDate(mutableList: MutableList<VadioFileInfo>?) {
        if (mutableList == null) {
        } else {
            this.mutableList.addAll(mutableList)
        }
        notifyDataSetChanged()
    }

    fun getDate(): MutableList<VadioFileInfo>? {
        if (mutableList == null)
            return mutableListOf<VadioFileInfo>()
        return mutableList
    }



    constructor(context: Context, backCall: BackCall) {
        this.context = context
        this.backCall = backCall
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {//表示没有歌曲
            return EmptyMusicHolder(LayoutInflater.from(context).inflate(R.layout.empty_music_item, parent, false)).init(backCall)
        } else if (viewType == 1) {
            return MusicHolder(LayoutInflater.from(context).inflate(R.layout.music_item, parent, false))
        } else {
            return EmptyMusicHolder(LayoutInflater.from(context).inflate(R.layout.empty_music_item, parent, false))
        }
    }

    class EmptyMusicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var view = itemView;
        fun init(backCall: BackCall?): EmptyMusicHolder {
            view!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    if (backCall != null)
                        backCall!!.deal(R.id.music_empty_to_search, "")
                }
            })
            return this
        }

        fun setDate(str: String) {
            view!!.findViewById<TextView>(R.id.search_file).setText(str)
        }

    }

    class MusicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var music_more: View = itemView!!.findViewById<View>(R.id.music_item_more)

        fun setDate(vadioFileInfo: VadioFileInfo): MusicHolder {
            itemView.findViewById<TextView>(R.id.music_author).text = (vadioFileInfo.artist) + "-" + vadioFileInfo.album
            itemView.findViewById<TextView>(R.id.music_name).text = vadioFileInfo.tilte

            Log.i("图片位置", vadioFileInfo.url!!)
//            Picasso.with(App.instance).load(vadioFileInfo.url!!).error(R.mipmap.icon1).into(itemView.findViewById<ImageView>(R.id.music_img))
//            MusicUtils().getMusicImg(vadioFileInfo.url!!, object : BackCall() {
//                override fun deal() {
//                }
//
//                override fun deal(tag: Any, vararg obj: Any) {
//                    when (tag) {
//                        "image" -> itemView.findViewById<ImageView>(R.id.music_img).setImageBitmap(obj[0] as Bitmap)
//                    }
//                }
//            })
            return this
        }
    }
}