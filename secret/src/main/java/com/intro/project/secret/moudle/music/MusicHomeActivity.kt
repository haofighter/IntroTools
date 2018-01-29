package com.intro.project.secret.moudle.music

import com.intro.hao.mytools.base.BackCall
import com.intro.project.secret.R
import com.intro.project.secret.base.BaseActiivty
import kotlinx.android.synthetic.main.activity_music_home.*

class MusicHomeActivity : BaseActiivty() {
    override fun LayoutID(): Int {
        return R.layout.activity_music_home
    }

    override fun initView() {
        super.initView()
        navigation.setTitle("我的music")

        mi_music.adapter = MiMusicAdapter(this, backCall)
    }

    var backCall = object : BackCall() {
        override fun deal() {
        }

        override fun deal(tag: Any, vararg obj: Any) {
        }
    }

}
