package com.intro.project.secret.moudle.note

import android.content.Intent
import android.net.Uri
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
import com.intro.hao.mytools.Utils.SomethingElseUtils
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.hao.mytools.customview.RecycleViewDivider
import com.intro.project.secret.MainActivity
import com.intro.project.secret.R
import com.intro.project.secret.base.BaseApp
import com.intro.project.secret.base.DrawarBaseActiivty
import com.intro.project.secret.model.NoteInfo
import com.intro.project.secret.widget.NoteWidgetProvider
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.activity_show_note_list.*
import kotlinx.android.synthetic.main.note_end_item.view.*
import kotlinx.android.synthetic.main.note_list_itemlayout.view.*


class ShowNoteListActivity : DrawarBaseActiivty() {
    override fun LayoutID(): Int {
        return R.layout.activity_show_note_list
    }

    //初始化标题栏
    override fun initNavigation() {
        navigation.setTitle("我的记事")
        navigation.setRightText("添加")
        navigation.setLeftImage(R.mipmap.home)
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.RIGHT_VIEW -> {
                        var intent = Intent(this@ShowNoteListActivity, EditNoteActivity::class.java)
                        intent.removeExtra(NoteWidgetProvider().noteListExtra) //清楚残留的id  防止添加记事本时出现覆盖
                        startActivity(intent)
                    }
                    NavigationTag.LEFT_VIEW -> {
                        startActivity(Intent(this@ShowNoteListActivity, MainActivity::class.java))
                    }
                }
                return true
            }
        })
    }


    private fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) true else false
    }

    override fun initView() {
        super.initView()
        recycle_view.layoutManager = LinearLayoutManager(this)
        //设置增加或删除条目的动画
        recycle_view.setItemAnimator(DefaultItemAnimator())
        recycle_view.addItemDecoration(RecycleViewDivider(this, DividerItemDecoration.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.gray_22)))
        recycle_view.setAdapter(noteItemAdapter)


        recycle_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i("列表长度", "" + recyclerView!!.height + "       " + (SystemUtils().getScreenSize(this@ShowNoteListActivity).heightPixels - navigation.height - SystemUtils().getDecorViewHight(this@ShowNoteListActivity)))
                if (recyclerView!!.height < SystemUtils().getScreenSize(this@ShowNoteListActivity).heightPixels - navigation.height - SystemUtils().getDecorViewHight(this@ShowNoteListActivity)) {
                } else {
                    if (isSlideToBottom(recyclerView)) {
                        noteItemAdapter.isEnding = false
                        recycle_view.post(Runnable {
                            noteItemAdapter.notifyDataSetChanged()
                        })

                        Log.i("滑动到了底部", "0000000000000");
                        Log.i("列表长度", "" + recyclerView!!.height + "       " + SystemUtils().getScreenSize(this@ShowNoteListActivity));
                    }
                }
            }
        })


        //刷新控件的监听
        swipe_refresh.setOnRefreshListener {
            updataNote()
            swipe_refresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        updataNote()
    }

    fun updataNote() {
        noteItemAdapter.notes = NoteInfo().queryAll() as MutableList<NoteInfo>
        Log.i("查询到的数据", noteItemAdapter.notes.size.toString());
        if (noteItemAdapter.notes.size == 0) {
            null_note_layout.visibility = View.VISIBLE
        } else {
            null_note_layout.visibility = View.GONE
            noteItemAdapter.notifyDataSetChanged()
        }
    }

    var noteItemAdapter = NoteItemAdapter(object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
            Log.i("点击时间触发", tag as String)
            when (tag) {
                "shortClick" -> {
                    var it = Intent(this@ShowNoteListActivity, EditNoteActivity::class.java)
                    it.putExtra(NoteWidgetProvider().noteListExtra, (obj[0] as NoteInfo).id)
                    startActivity(it)
                }
            }
        }
    })

}


//关联记事的列表的adapter
class NoteItemAdapter(backCall: BackCall) : Adapter<RecyclerView.ViewHolder>() {
    var notes: MutableList<NoteInfo> = mutableListOf()
    var backCall = backCall
    var isEnding = true//判断是否加载完
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return NoteHolder(LayoutInflater.from(BaseApp.instance.applicationContext).inflate(R.layout.note_list_itemlayout, null))
        } else {
            return EndHolder(LayoutInflater.from(BaseApp.instance.applicationContext).inflate(R.layout.note_end_item, null))
        }
    }

    override fun getItemCount(): Int {
        return notes.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position == notes.size) {
            if (isEnding) {
                (holder as EndHolder).ending()
            } else {
                (holder as EndHolder).loading()
            }
        } else {
            (holder as NoteHolder).initView(notes.get(position)).addBackCall(backCall)
        }


    }

    override fun getItemViewType(position: Int): Int {
        if (position == notes.size) {
            return 2
        } else {
            return 1
        }
    }


    class NoteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var noteInfo: NoteInfo? = NoteInfo()
        fun initView(noteInfo: NoteInfo): NoteHolder {
            this.noteInfo = noteInfo
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SystemUtils().dip2px(BaseApp.instance.applicationContext, 100F))

            if (noteInfo.constent.startsWith("<progress id=")) {
                itemView.note_title.text = "【图片】"
            } else {
                itemView.note_title.text = noteInfo.constent.substring(0, if (noteInfo.constent.length > 10) 10 else noteInfo.constent.length).split("<")[0]
            }

            //用于判断是否存在图片
            var imgs = SomethingElseUtils().searchImgString(noteInfo.constent)
            if (imgs.size > 0) {
                Log.i("图片的路径", imgs[0].replace("src=\"", "").replace("\"", ""))
                //获取到第一张图片 在条目中进行显示
                itemView.note_image_content.setImageURI(Uri.parse(imgs[0].replace("src=\"", "").replace("\"", "")))
                itemView.note_image_content.visibility = View.VISIBLE
            } else {
                itemView.note_image_content.visibility = View.GONE
            }
            itemView.note_time.text = DataUtils().FormatDate(noteInfo.time, "MM.dd HH:ss")


            if (noteInfo.remindTime == 0L) {
                itemView.clock_time_show_layout.visibility = View.GONE
            } else {
                itemView.clock_time_show_layout.visibility = View.VISIBLE
                itemView.clock_time.text = DataUtils().FormatDate(noteInfo.remindTime, "MM.dd HH:ss")
            }
            return this
        }

        fun addBackCall(backCall: BackCall) {
//            itemView.note_list_item.setOnClickListener(object : View.OnClickListener {
//                override fun onClick(p0: View?) {
//                    backCall!!.deal("shortClick", noteInfo as Any)
//                }
//            })

            itemView.note_list_item.setOnClickListener(View.OnClickListener {
                backCall!!.deal("shortClick", noteInfo as Any)
            })
            itemView.note_list_item.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(p0: View?): Boolean {
                    backCall!!.deal("longClick", noteInfo as Any)
                    return true
                }
            })
        }

    }

    class EndHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun loading() {
            itemView.loading.visibility = View.VISIBLE
        }

        fun ending() {
            itemView.loading.visibility = View.GONE
        }
    }

}