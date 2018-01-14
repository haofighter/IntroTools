package com.intro.hao.mytools.Utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.intro.hao.mytools.R
import com.intro.hao.mytools.Utils.view.LoadingView
import com.intro.hao.mytools.base.App
import com.intro.hao.mytools.base.BackCall

/**
 * Created by haozhang on 2018/1/11.
 */
class DialogUtils {
    private object Holder {
        val INSTANCE = DialogUtils()
    }

    companion object {
        val instance: DialogUtils by lazy { Holder.INSTANCE }
    }

    /**
     * 两个按键（确定，取消）的dialog  点击外部不消失  按钮最少存在一个
     *
     * @param tv_titel 标题   null 表示不显示标题
     * @param content
     * @param conf     左边按钮   null 表示不显示此按钮
     * @param canc     右边按钮   null 表示不显示此按钮
     * @param context
     * @param call     回调
     * @return Dialog
     * @throws
     * @since 1.0.0
     */
    fun showInfoDialog(context: Context,
                       tv_titel: String?, content: String, conf: String?, canc: String?,
                       call: BackCall?): Dialog {
        val dlg = Dialog(context, R.style.noDialogTheme)
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater!!.inflate(
                R.layout.dialog_show_info, null) as LinearLayout
        val title = layout.findViewById<TextView>(R.id.title)
        if (tv_titel == null) {
            title.visibility = View.GONE
        } else {
            title.visibility = View.VISIBLE
            title.text = tv_titel
        }
        val cont = layout.findViewById<TextView>(R.id.content)
        cont.text = content
        val confirm = layout.findViewById<TextView>(R.id.confirm)
        val cancel = layout.findViewById<TextView>(R.id.cancel)
        if (conf == null) {
            confirm.visibility = View.GONE
            layout.findViewById<View>(R.id.line2).visibility = View.GONE
        } else {
            confirm.visibility = View.VISIBLE
            confirm.text = conf
        }
        if (canc == null) {
            layout.findViewById<View>(R.id.line2).visibility = View.GONE
            cancel.visibility = View.GONE
        } else {
            cancel.visibility = View.VISIBLE
            cancel.text = canc
        }

        if (conf == null && canc == null) {
            layout.findViewById<View>(R.id.line2).visibility = View.GONE
            layout.findViewById<View>(R.id.line1).visibility = View.GONE
            cancel.visibility = View.GONE
            confirm.text = "确认"
            confirm.visibility = View.VISIBLE
        }

        layout.findViewById<View>(R.id.confirm).setOnClickListener {
            if (call != null) {
                call!!.deal(R.id.confirm, dlg)
                dlg.dismiss()
            } else {
                dlg.dismiss()
            }
        }
        layout.findViewById<View>(R.id.cancel).setOnClickListener(
                object : View.OnClickListener {
                    override fun onClick(v: View) {
                        if (call != null) {
                            call!!.deal((R.id.cancel).toString() + "", dlg)
                            dlg.dismiss()
                        } else {
                            dlg.dismiss()
                        }
                    }
                })
        val w = dlg.window
        val lp = w!!.attributes
        lp.gravity = Gravity.CENTER
        dlg.onWindowAttributesChanged(lp)
        dlg.setCanceledOnTouchOutside(true)
        dlg.setContentView(layout)
        dlg.setCancelable(false)
        return dlg
    }


    internal var dlg: Dialog? = null
    /**
     * 弹出加载显示窗口
     *
     * @param context
     * @return
     */
    fun LoadingDialog(context: Context, isCustom: Boolean): Dialog {
        if (dlg == null) {
            val load = LoadingView(context, isCustom)
            dlg = Dialog(context, R.style.noDialogTheme)
            val w = dlg!!.window
            val lp = w!!.attributes
            lp.gravity = Gravity.CENTER
            dlg!!.onWindowAttributesChanged(lp)
            dlg!!.setCanceledOnTouchOutside(true)
            dlg!!.setContentView(load.view)
            dlg!!.setCancelable(false)
        }
        return dlg as Dialog
    }

}

