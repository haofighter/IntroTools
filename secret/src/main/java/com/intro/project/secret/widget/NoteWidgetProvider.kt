package com.intro.project.secret.widget

import android.app.PendingIntent
import android.app.RemoteAction
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.project.secret.MainActivity
import com.intro.project.secret.R
import android.widget.Toast
import com.intro.project.secret.moudle.note.EditNoteActivity


/**
 * Created by hao on 2018/3/7.
 */
class NoteWidgetProvider : AppWidgetProvider() {
    internal val noteTitleClick = "com.intro.project.sercret.note.title.CLICK"
    internal val noteWidgetRefresh = "com.intro.project.sercret.note.refresh"
    internal val noteListClick = "com.intro.project.sercret.note.list.CLICK_ACTION"
    val noteListExtra = "com.intro.project.sercret.not.EXTRA"

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        Log.i("小部件", "小部件刷新")
        for (id in appWidgetIds!!.iterator()) {
            Log.i("小部件", "小部件id循环" + id)
            // 获取AppWidget对应的视图
            val rv = RemoteViews(context!!.getPackageName(), R.layout.note_widget_layout)

            //设置标题点击事件
//            val btIntent = Intent().setAction(noteTitleClick)
//            val btPendingIntent = PendingIntent.getBroadcast(context, 0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//            rv.setOnClickPendingIntent(R.id.weiget_note_title, btPendingIntent)
            val skipIntent = Intent(context, EditNoteActivity::class.java)
            val pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            rv.setOnClickPendingIntent(R.id.weiget_note_title, pi)



            val serviceIntent = Intent(context, NoteListWeigetService::class.java)
            rv.setRemoteAdapter(R.id.content_list, serviceIntent)


            val gridIntent = Intent()
            gridIntent.action = noteListClick
            gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            // 设置intent模板
            rv.setPendingIntentTemplate(R.id.content_list, pendingIntent)
            // 调用集合管理器对集合进行更新
            appWidgetManager!!.updateAppWidget(id, rv)
        }


        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        remoteViews.setOnClickPendingIntent(R.id.note_content, PendingIntent.getBroadcast(context, R.id.note_content, Intent(noteWidgetClick), PendingIntent.FLAG_CANCEL_CURRENT));
//        if (appWidgetIds != null) {
//            for (item: Int in appWidgetIds) {
//                appWidgetManager!!.updateAppWidget(item, remoteViews);
//            }
//        }
    }

    fun getAllWidght(context: Context?): IntArray {
        var appWidgetManager = AppWidgetManager.getInstance(context)
        return appWidgetManager.getAppWidgetIds(ComponentName(context, NoteWidgetProvider::class.java))
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        when (intent!!.action) {
            noteTitleClick -> {
                ToastUtils().showMessage("小部件被点击了")
            }
            noteListClick -> {
                // 接受“gridview”的点击事件的广播
                val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID)
                val viewIndex = intent.getIntExtra(noteListExtra, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }


            noteWidgetRefresh -> {
                Log.i("小部件", "小部件刷新广播被接收")
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(getAllWidght(context), R.id.content_list);
            }
        }
//        context!!.startActivity(Intent(context, MainActivity::class.java))

    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }


}
