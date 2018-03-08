package com.intro.project.secret.moudle.note

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intro.hao.mytools.Utils.DataUtils
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.hao.mytools.customview.RecycleViewDivider
import com.intro.project.secret.MainActivity
import com.intro.project.secret.R
import com.intro.project.secret.base.BaseApp
import com.intro.project.secret.base.DrawarBaseActiivty
import com.intro.project.secret.model.NoteInfo
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.activity_show_note_list.*
import kotlinx.android.synthetic.main.note_list_itemlayout.view.*


class ShowNoteListActivity : DrawarBaseActiivty() {
    override fun LayoutID(): Int {
        return R.layout.activity_show_note_list
    }

    override fun initView() {
        super.initView()
        navigation.setTitle("我的记事")
        navigation.setRightText("添加")
        navigation.setLeftImage(R.mipmap.home)
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.RIGHT_VIEW -> {
                        startActivity(Intent(this@ShowNoteListActivity, EditNoteActivity::class.java))
                    }
                    NavigationTag.LEFT_VIEW -> {
                        startActivity(Intent(this@ShowNoteListActivity, MainActivity::class.java))
                    }
                }
                return true
            }
        })

        recycle_view.layoutManager = LinearLayoutManager(this)
        //设置增加或删除条目的动画
        recycle_view.setItemAnimator(DefaultItemAnimator())
        recycle_view.addItemDecoration(RecycleViewDivider(this, DividerItemDecoration.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.gray_22)))

        recycle_view.setAdapter(noteItemAdapter)

    }

    override fun onResume() {
        super.onResume()
        noteItemAdapter.notes = NoteInfo().queryAll() as MutableList<NoteInfo>
        noteItemAdapter.notifyDataSetChanged()
    }

    var noteItemAdapter = NoteItemAdapter(object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
            Log.i("点击时间触发", tag as String)
        }
    })

}


//关联记事的列表的adapter
class NoteItemAdapter(backCall: BackCall) : Adapter<RecyclerView.ViewHolder>() {

    var notes: MutableList<NoteInfo> = mutableListOf()
    var backCall = backCall
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return NoteHolder(LayoutInflater.from(BaseApp.instance.applicationContext).inflate(R.layout.note_list_itemlayout, null))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as NoteHolder).initView(notes.get(position)).addBackCall(backCall)
    }


    class NoteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var noteInfo: NoteInfo? = NoteInfo()
        fun initView(noteInfo: NoteInfo): NoteHolder {
            this.noteInfo = noteInfo
            itemView.note_title.text = noteInfo.constent.substring(0, if (noteInfo.constent.length > 10) 10 else noteInfo.constent.length)
            itemView.note_time.text = DataUtils().FormatDate(noteInfo.time, "MM.dd HH:ss")
            return this
        }

        fun addBackCall(backCall: BackCall) {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    backCall!!.deal("shortClick", noteInfo as Any)
                }
            })

//            itemView.setOnClickListener(View.OnClickListener {
//                backCall!!.deal("shortClick", noteInfo as Any)
//            })
            itemView.note_list_item.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {
                    backCall!!.deal("longClick", noteInfo as Any)
                    return true
                }
            })
        }

    }

}