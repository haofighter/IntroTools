package com.intro.project.secret.moudle.note

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import com.intro.hao.mytools.Utils.DialogUtils
import com.intro.hao.mytools.Utils.SoftKeyBoardListener
import com.intro.hao.mytools.Utils.SystemUtils
import com.intro.hao.mytools.Utils.ToastUtils
import com.intro.hao.mytools.Utils.animalUtil.AnimalUtils
import com.intro.hao.mytools.Utils.animalUtil.AnimalUtilsModel
import com.intro.hao.mytools.base.BackCall
import com.intro.hao.mytools.customview.NavigationBar
import com.intro.hao.mytools.customview.NavigationTag
import com.intro.hao.mytools.customview.richeditor.RichEditor
import com.intro.project.secret.R
import com.intro.project.secret.base.DrawarBaseActiivty
import com.intro.project.secret.model.NoteInfo
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.flowing_layout.*
import kotlinx.android.synthetic.main.font_set_layout.*
import java.util.*


class EditNoteActivity : DrawarBaseActiivty(), View.OnClickListener, RichEditor.OnTouchScreenListener {
    override fun onReleaseScreen() {

    }

    override fun onTouchScreen() {
        multiple_actions.collapse()
    }


    val RICH_IMAGE_CODE = 0x33

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.richEditor -> {
                multiple_actions.collapse()
            }
            R.id.text_bold -> { //粗体
                richEditor.setBold()
            }
            R.id.text_italic -> { //斜体
                richEditor.setItalic()
            }
            R.id.text_strikethrough -> {//删除线
                richEditor.setStrikeThrough()
            }
            R.id.text_blockquote -> {//块引用
                richEditor.setBlockquote(text_blockquote.isChecked)
            }
            R.id.text_h1 -> {//H1字体
                richEditor.setHeading(1)
            }
            R.id.text_h2 -> {//H2字体
                richEditor.setHeading(2)
            }
            R.id.text_h3 -> {//H3字体
                richEditor.setHeading(3)
            }
            R.id.text_h4 -> {//H4字体
                richEditor.setHeading(4)
            }
            R.id.add_image -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)
                } else {
                    val picture = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(picture, RICH_IMAGE_CODE)
                }
            }
            R.id.add_link -> {
                showInsertLinkDialog()
            }
            R.id.add_split -> {
                richEditor.insertHr()
            }
            R.id.action_undo -> {
                richEditor.undo()
            }
            R.id.action_redo -> {
                richEditor.redo()
            }
            R.id.action_font -> {//显示隐藏 字体设置布局
                if (font_set_layout.visibility == View.GONE) {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(font_set_layout, AnimalUtils.AnimalType.bottom)).addTranAnimal(true).setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {

                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            font_set_layout.visibility = View.VISIBLE
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    }).startAnimal()
                } else {
                    AnimalUtils().setAnimalModel(AnimalUtilsModel(font_set_layout, AnimalUtils.AnimalType.bottom)).addTranAnimal(false).setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {

                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            font_set_layout.visibility = View.GONE
                        }

                        override fun onAnimationStart(p0: Animation?) {
                        }
                    }).startAnimal()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val picture = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(picture, RICH_IMAGE_CODE)
            } else {
                DialogUtils().showInfoDialog(this, "提示", "你还为获取到读取权限，是否进行设置？", "去设置", "取消", object : BackCall() {
                    override fun deal(tag: Any, vararg obj: Any) {
                        getAppDetailSettingIntent()
                    }

                    override fun deal() {
                    }
                }).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RICH_IMAGE_CODE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            val c = this.contentResolver.query(selectedImage, filePathColumns, null, null, null)
            c.moveToFirst()
            val columnIndex = c.getColumnIndex(filePathColumns[0])
            val picturePath = c.getString(columnIndex)
            Log.i("dgs", "picturePath----" + picturePath)
            //插入图片
            richEditor.setProgress(picturePath)
            uploadImage(picturePath)
            richEditor.insertImage(picturePath, "图片", "来自....的图片")
            c.close()
        }
    }

    fun uploadImage(picturePath: String) {
        var i = 0
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread(object : TimerTask() {
                    override fun run() {
                        richEditor.refreshProgess(picturePath, i++)
                    }
                })
            }
        }, 0, 200)
    }

    /**
     * 插入链接Dialog
     */
    private fun showInsertLinkDialog() {
        val adb = AlertDialog.Builder(this)
        var linkDialog = adb.create()
        val view = layoutInflater.inflate(R.layout.dialog_insertlink, null)
        val etext = view.findViewById<TextView>(R.id.et_link_address).text
        Selection.setSelection(etext as Spannable?, etext.length)

        //点击确实的监听
        view.findViewById<TextView>(R.id.btn_ok).setOnClickListener(View.OnClickListener {
            val linkAddress = view.findViewById<TextView>(R.id.et_link_address).text.toString()
            val linkTitle = view.findViewById<TextView>(R.id.et_link_title).text.toString()

            if (linkAddress.endsWith("http://") || TextUtils.isEmpty(linkAddress)) {
                ToastUtils().showMessage("请输入超链接地址")
            } else if (TextUtils.isEmpty(linkTitle)) {
                ToastUtils().showMessage("请输入超链接标题")
            } else {
                richEditor.insertLink(linkAddress, linkTitle)
                linkDialog.dismiss()
            }
        })
        //点击取消的监听
        view.findViewById<TextView>(R.id.btn_cancel).setOnClickListener(View.OnClickListener { linkDialog.dismiss() })

        linkDialog.setCancelable(false)
        linkDialog.setView(view, 0, 0, 0, 0) // 设置 view
        linkDialog.show()
    }

    override fun initView() {
        super.initView()
        navigation.setRightText("保存")
        navigation.setTitle("写记事")
        navigation.setListener(object : NavigationBar.NavigationListener {
            override fun onButtonClick(button: NavigationTag): Boolean {
                when (button) {
                    NavigationTag.RIGHT_VIEW -> {
                        Log.i("TAG", "点击了完成")
                        saveNote()
                    }
                }
                return true
            }
        })
        //AndroidAdjustResizeBugFixClass(this)
        // edit_note_layout.addOnLayoutChangeListener(this) //用于监听软键盘
        richEditor.setOnTouchScreenListener(this)
        initRichEditSettingView()
        initRichEditSetting()

        SoftKeyBoardListener.setListener(this, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                richEditor.layout(richEditor.left, richEditor.top, richEditor.right, richEditor.bottom - height)
                bottom_layout.layout(font_set_layout.left, richEditor.bottom, font_set_layout.right, richEditor.bottom + bottom_layout.height)

                Log.i("", "修改后的高度" + richEditor.bottom + "键盘显示 高度" + height + "屏幕的高度" + SystemUtils().getScreenSize(this@EditNoteActivity).heightPixels + "     底部布局的高度" + bottom_layout.height + "底部布局" + bottom_layout.top + "---" + bottom_layout.bottom)
                ToastUtils().showMessage("键盘显示 高度" + height + "   屏幕的高度" + SystemUtils().getScreenSize(this@EditNoteActivity).heightPixels)
            }

            override fun keyBoardHide(height: Int) {
                richEditor.layout(richEditor.left, richEditor.top, richEditor.right, SystemUtils().getScreenSize(this@EditNoteActivity).heightPixels)
                ToastUtils().showMessage("键盘隐藏 高度" + height)
            }
        })
    }


    //保存笔记
    fun saveNote() {
        if (richEditor.html == null || richEditor.html.equals("")) {
            DialogUtils().showInfoDialog(this, "提示", "您还未编辑内容\n是否退出？", "退出", "继续", object : BackCall() {
                override fun deal() {
                }

                override fun deal(tag: Any, vararg obj: Any) {
                    when (tag) {
                        R.id.confirm -> {
                            NoteInfo(richEditor.html, System.currentTimeMillis()).save()
                            finish()
                        }
                    }
                }
            })
        } else {
            finish()
        }
    }

    private fun initRichEditSettingView() {
//        action_a.setElseView(LayoutInflater.from(this).inflate(R.layout.font_set_layout, null))
//        action_a.setOnClickListener(this)
//        action_b.setElseView(LayoutInflater.from(this).inflate(R.layout.add_fun_set_layout, null))
//        action_b.setOnClickListener(this)

        text_bold.setOnClickListener(this)
        text_blockquote.setOnClickListener(this)
        text_italic.setOnClickListener(this)
        text_strikethrough.setOnClickListener(this)
        text_h1.setOnClickListener(this)
        text_h2.setOnClickListener(this)
        text_h3.setOnClickListener(this)
        text_h4.setOnClickListener(this)
        action_redo.setOnClickListener(this)
        action_undo.setOnClickListener(this)
        action_font.setOnClickListener(this)
        add_image.setOnClickListener(this)
        add_link.setOnClickListener(this)
        add_split.setOnClickListener(this)
//        add_image.setOnClickListener(this)
//        add_link.setOnClickListener(this)
//        add_split.setOnClickListener(this)
    }

    private fun initRichEditSetting() {

        richEditor.setEditorFontSize(15)
        richEditor.setPadding(10, 10, 10, 50)
        richEditor.setPlaceholder("填写笔记内容")
        /**
         *获取点击出文本的标签类型
         */
        richEditor.setOnDecorationChangeListener(object : RichEditor.OnDecorationStateListener {
            override fun onStateChangeListener(text: String, types: List<RichEditor.Type>) {
//                text_bold.isChecked = types.contains(RichEditor.Type.BOLD)
//                text_italic.isChecked = types.contains(RichEditor.Type.ITALIC)
//                //块引用
//                text_strikethrough.isChecked = types.contains(RichEditor.Type.STRIKETHROUGH)
//                text_h1.isChecked = types.contains(RichEditor.Type.H1)
//                ext_h3.isChecked = types.contains(RichEditor.Type.H3)
//                text_h4.isChecked = types.contains(RichEditor.Type.H4)
            }
        })

    }

    override fun LayoutID(): Int {
        return R.layout.activity_edit_note
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogUtils().showInfoDialog(this, "提示", "是否保存？", "退出", "继续", object : BackCall() {
                override fun deal() {
                }

                override fun deal(tag: Any, vararg obj: Any) {
                    when (tag) {
                        R.id.confirm -> {
//                            showLoading()
                            finish()
                        }
                    }
                }
            })
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
